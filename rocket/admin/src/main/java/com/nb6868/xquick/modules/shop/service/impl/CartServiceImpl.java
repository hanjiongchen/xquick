package com.nb6868.xquick.modules.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nb6868.xquick.booster.service.impl.CrudServiceImpl;
import com.nb6868.xquick.modules.shop.dao.CartDao;
import com.nb6868.xquick.modules.shop.dto.CartDTO;
import com.nb6868.xquick.modules.shop.entity.CartEntity;
import com.nb6868.xquick.modules.shop.service.CartService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 购物车
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class CartServiceImpl extends CrudServiceImpl<CartDao, CartEntity, CartDTO> implements CartService {

    @Override
    public QueryWrapper<CartEntity> getWrapper(String method, Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<CartEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }

}
