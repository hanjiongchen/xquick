package com.nb6868.xquick.modules.shop.service;

import com.nb6868.xquick.booster.service.CrudService;
import com.nb6868.xquick.modules.shop.dto.OrderChangeReceiverRequest;
import com.nb6868.xquick.modules.shop.dto.OrderDTO;
import com.nb6868.xquick.modules.shop.entity.OrderEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public interface OrderService extends CrudService<OrderEntity, OrderDTO> {

    /**
     * 通过订单号获取订单
     */
    OrderEntity getByNo(String no);

    /**
     * 修改订单收件信息
     */
    boolean changeReceiver(OrderChangeReceiverRequest request);

    /**
     * 生成订单号
     */
    String generateOrderSn();

    /**
     * 取消订单
     */
    boolean cancel(Long id);

    /**
     * 取消超时未支付订单
     *
     * @param second 超时秒数
     */
    boolean cancelUnPaidOrder(long second);


    boolean checkOrder(Long orderId);

    /**
     * 支付回调
     */
    boolean payNotify(Integer payType, String orderNo, BigDecimal payFee, Date payTime);

}
