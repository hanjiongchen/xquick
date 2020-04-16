package com.nb6868.xquick.modules.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nb6868.xquick.booster.service.impl.CrudServiceImpl;
import com.nb6868.xquick.booster.util.WrapperUtils;
import com.nb6868.xquick.modules.shop.dao.OrderItemDao;
import com.nb6868.xquick.modules.shop.dto.OrderItemDTO;
import com.nb6868.xquick.modules.shop.entity.OrderItemEntity;
import com.nb6868.xquick.modules.shop.service.OrderItemService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 订单明细
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class OrderItemServiceImpl extends CrudServiceImpl<OrderItemDao, OrderItemEntity, OrderItemDTO> implements OrderItemService {

    @Override
    public QueryWrapper<OrderItemEntity> getWrapper(String method, Map<String, Object> params){
        return new WrapperUtils<OrderItemEntity>(new QueryWrapper<>(), params)
                .eq("orderId", "user_id")
                .getQueryWrapper();
    }

}
