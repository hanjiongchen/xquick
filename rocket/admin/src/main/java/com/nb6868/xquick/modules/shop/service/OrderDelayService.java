package com.nb6868.xquick.modules.shop.service;

/**
 * 延迟订单处理
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public interface OrderDelayService {

    /**
     * 延迟订单
     * @param orderId 订单Id
     * @param expireTime 延时时间
     */
    void orderDelay(Long orderId, long expireTime);

}
