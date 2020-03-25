package co.xquick.modules.shop.service.impl;

import co.xquick.booster.exception.ErrorCode;
import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.booster.util.WrapperUtils;
import co.xquick.booster.validator.AssertUtils;
import co.xquick.modules.log.controller.ErrorController;
import co.xquick.modules.shop.dao.ReceiverDao;
import co.xquick.modules.shop.dto.ReceiverDTO;
import co.xquick.modules.shop.entity.ReceiverEntity;
import co.xquick.modules.shop.service.ReceiverService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

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
                .eq("defaultItem", "shop_receiver.status")
                .eq("userId", "shop_receiver.user_id")
                .eq("mobile", "shop_receiver.mobile")
                .like("address", "shop_receiver.address")
                .like("consignee", "shop_receiver.consignee")
                .getQueryWrapper()
                .eq("shop_receiver.deleted", 0);
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

}
