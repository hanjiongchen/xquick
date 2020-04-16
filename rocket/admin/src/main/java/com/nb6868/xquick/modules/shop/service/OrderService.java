package com.nb6868.xquick.modules.shop.service;

import com.nb6868.xquick.booster.service.CrudService;
import com.nb6868.xquick.modules.shop.dto.OrderChangeReceiverRequest;
import com.nb6868.xquick.modules.shop.dto.OrderDTO;
import com.nb6868.xquick.modules.shop.entity.OrderEntity;

/**
 * 订单
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public interface OrderService extends CrudService<OrderEntity, OrderDTO> {

    /**
     * 修改订单收件信息
     */
    boolean changeReceiver(OrderChangeReceiverRequest request);

}
