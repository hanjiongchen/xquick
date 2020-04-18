package com.nb6868.xquick.modules.shop.service;

import com.nb6868.xquick.modules.shop.entity.OrderEntity;

/**
 * 延迟订单处理
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public interface OrderDelayService {

    /**
     * 延迟订单
     * @param order 订单
     * @param expireTime 延时时间
     */
    void orderDelay(OrderEntity order, long expireTime);

}
