package co.xquick.modules.shop.service.impl;

import co.xquick.booster.exception.ErrorCode;
import co.xquick.booster.pojo.Const;
import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.booster.util.WrapperUtils;
import co.xquick.booster.validator.AssertUtils;
import co.xquick.modules.shop.dao.ReceiverDao;
import co.xquick.modules.shop.dto.ReceiverDTO;
import co.xquick.modules.shop.entity.ReceiverEntity;
import co.xquick.modules.shop.service.ReceiverService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Map;

/**
 * 收件地址
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class ReceiverServiceImpl extends CrudServiceImpl<ReceiverDao, ReceiverEntity, ReceiverDTO> implements ReceiverService {

    @Override
    public QueryWrapper<ReceiverEntity> getWrapper(String method, Map<String, Object> params) {
        return new WrapperUtils<ReceiverEntity>(new QueryWrapper<>(), params)
                .eq("defaultItem", "shop_receiver.default_item")
                .eq("userId", "shop_receiver.user_id")
                .eq("mobile", "shop_receiver.mobile")
                .like("address", "shop_receiver.address")
                .like("consignee", "shop_receiver.consignee")
                .getQueryWrapper()
                .eq("shop_receiver.deleted", 0);
    }

    @Override
    protected void afterSaveOrUpdateDto(boolean ret, ReceiverDTO dto, ReceiverEntity existedEntity, int type) {
        if (dto.getDefaultItem() == 1) {
            // 为默认地址,将其它地址设置为非默认
            update().eq("user_id", dto.getUserId()).set("default_item", 0).ne("id", dto.getId()).update(new ReceiverEntity());
        } else {
            // 为非默认,查一下用户有没有默认地址
            if (!SqlHelper.retBool(query().eq("user_id", dto.getUserId()).eq("default_item", 1).count())) {
                // 没有默认地址
                // 将该会员最新一条地址设置为非默认
                update().set("default_item", 1)
                        .eq("user_id", dto.getUserId())
                        .orderByDesc("create_time")
                        .last("limit 1")
                        .update(new ReceiverEntity());
            }
        }
    }

    @Override
    public boolean setDefaultItem(Long id) {
        // 获取地址
        ReceiverEntity entity = getById(id);
        AssertUtils.isEmpty(entity, ErrorCode.DB_RECORD_NOT_EXISTED);
        // 将该会员其它地址设置为非默认
        update().eq("user_id", entity.getUserId()).set("default_item", 0).ne("id", id).update(new ReceiverEntity());
        // 将该地址设置为默认项
        update().eq("id", id).set("default_item", 1).update(new ReceiverEntity());
        return true;
    }



    @Override
    public boolean logicDeleteById(Serializable id) {
        // 获取地址
        ReceiverEntity entity = getById(id);
        AssertUtils.isEmpty(entity, ErrorCode.DB_RECORD_NOT_EXISTED);
        if (entity.getDefaultItem() == Const.BooleanEnum.TRUE.value()) {
            // 删除了一个默认项,需要将另外一个设置为默认项
            // 将该会员最新一条地址设置为非默认
            update().set("default_item", 1)
                    .eq("user_id", entity.getUserId())
                    .ne("id", id)
                    .orderByDesc("create_time")
                    .last("limit 1")
                    .update(new ReceiverEntity());
        }
        return super.logicDeleteById(id);
    }
}
