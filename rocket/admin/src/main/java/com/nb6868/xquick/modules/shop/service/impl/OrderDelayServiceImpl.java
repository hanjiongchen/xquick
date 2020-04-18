package com.nb6868.xquick.modules.shop.service.impl;

import com.nb6868.xquick.booster.pojo.ObjectDelay;
import com.nb6868.xquick.modules.shop.entity.OrderEntity;
import com.nb6868.xquick.modules.shop.service.OrderDelayService;
import com.nb6868.xquick.modules.shop.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.DelayQueue;

/**
 * 延迟订单处理Service
 * https://www.cnblogs.com/darendu/p/10074650.html
 * https://blog.csdn.net/qq_22075041/article/details/80399668
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Log4j2
@Service
public class OrderDelayServiceImpl implements OrderDelayService {

    @Autowired
    private OrderService orderService;

    /**
     * 检测到弹出订单
     */
    private Thread takeOrder;

    private static DelayQueue<ObjectDelay<Long>> delayQueue = new DelayQueue<>();

    @Override
    public void orderDelay(Long orderId, long expireTime) {
        ObjectDelay<Long> objectDelay = new ObjectDelay<>(orderId, expireTime);
        delayQueue.put(objectDelay);
        log.info("push order");
    }

    private class TakeOrder implements Runnable {

        private OrderService orderService;

        public TakeOrder(OrderService orderService) {
            this.orderService = orderService;
        }

        @Override
        public void run() {
            log.info("OrderDelayServiceImpl run");
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    ObjectDelay<Long> objectDelay = delayQueue.take();
                    if (objectDelay.getData() != null) {
                        orderService.checkOrder(objectDelay.getData());
                    }
                } catch (InterruptedException e) {
                    log.info("OrderDelayServiceImpl exception" + e.getMessage());
                    e.printStackTrace();
                }
            }
            log.info("OrderDelayServiceImpl stop");
        }
    }

    @PostConstruct
    public void init() {
        log.info("OrderDelayServiceImpl init");
        takeOrder = new Thread(new TakeOrder(orderService));
        takeOrder.start();
    }

    @PreDestroy
    public void close() {
        log.info("OrderDelayServiceImpl close");
        takeOrder.interrupt();
    }

}
