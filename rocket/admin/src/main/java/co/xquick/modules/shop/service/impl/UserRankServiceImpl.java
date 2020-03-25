package co.xquick.modules.shop.service.impl;

import co.xquick.booster.exception.ErrorCode;
import co.xquick.booster.pojo.Const;
import co.xquick.booster.util.WrapperUtils;
import co.xquick.booster.validator.AssertUtils;
import co.xquick.modules.shop.entity.StoreEntity;
import co.xquick.modules.shop.service.SpuService;
import co.xquick.modules.uc.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.modules.shop.dao.UserRankDao;
import co.xquick.modules.shop.dto.UserRankDTO;
import co.xquick.modules.shop.entity.UserRankEntity;
import co.xquick.modules.shop.service.UserRankService;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Map;

/**
 * 用户等级
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class UserRankServiceImpl extends CrudServiceImpl<UserRankDao, UserRankEntity, UserRankDTO> implements UserRankService {

    @Autowired
    private UserService userService;

    @Override
    public QueryWrapper<UserRankEntity> getWrapper(String method, Map<String, Object> params) {
        return new WrapperUtils<UserRankEntity>(new QueryWrapper<>(), params)
                .like("name", "name")
                .getQueryWrapper();
    }

    @Override
    protected void beforeSaveOrUpdateDto(UserRankDTO dto, int type) {
        if (dto.getDefaultItem() == Const.BooleanEnum.TRUE.value()) {
            // 默认
            AssertUtils.isTrue(hasDuplicated(dto.getId(), "default_item", 1), ErrorCode.COMMON_ERROR, "只能存在一个默认等级");
        } else {
            // 非默认
            AssertUtils.isFalse(hasDuplicated(dto.getId(), "default_item", 1), ErrorCode.COMMON_ERROR, "必须有一个默认等级");
        }
    }

    @Override
    public boolean logicDeleteById(Serializable id) {
        AssertUtils.isTrue(SqlHelper.retBool(userService.query().eq("shop_user_rank_id", id).count()), ErrorCode.COMMON_ERROR, "级别下存在会员,不允许删除");
        return super.logicDeleteById(id);
    }

}
