package com.nb6868.xquick.modules.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nb6868.xquick.booster.service.impl.CrudServiceImpl;
import com.nb6868.xquick.booster.util.WrapperUtils;
import com.nb6868.xquick.modules.shop.ShopConst;
import com.nb6868.xquick.modules.shop.dao.OrderDao;
import com.nb6868.xquick.modules.shop.dto.OrderChangeReceiverRequest;
import com.nb6868.xquick.modules.shop.dto.OrderDTO;
import com.nb6868.xquick.modules.shop.entity.OrderEntity;
import com.nb6868.xquick.modules.shop.entity.OrderLogEntity;
import com.nb6868.xquick.modules.shop.service.OrderLogService;
import com.nb6868.xquick.modules.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 订单
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class OrderServiceImpl extends CrudServiceImpl<OrderDao, OrderEntity, OrderDTO> implements OrderService {

    @Autowired
    OrderLogService orderLogService;

    @Override
    public QueryWrapper<OrderEntity> getWrapper(String method, Map<String, Object> params){
        return new WrapperUtils<OrderEntity>(new QueryWrapper<>(), params)
                .like("no", "no")
                .eq("userId", "user_id")
                .eq("status", "status")
                // 创建时间区间
                .ge("startCreateTime", "create_time")
                .le("endCreateTime", "create_time")
                .and("search", queryWrapper -> {
                    String search = (String) params.get("search");
                    queryWrapper.like("no", search);
                })
                .and("receiverSearch", queryWrapper -> {
                    String search = (String) params.get("receiverSearch");
                    queryWrapper.like("receiver_consignee", search)
                            .or().like("receiver_mobile", search)
                            .or().like("receiver_address", search)
                            .or().like("receiver_region_name", search);
                })
                .getQueryWrapper();
    }

    @Override
    public boolean changeReceiver(OrderChangeReceiverRequest request) {
        return false;
    }


    @Override
    // TODO 这里应该产生一个唯一的订单，但是实际上这里仍然存在两个订单相同的可能性
    public String generateOrderSn() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
        String now = df.format(LocalDate.now());
        String orderSn = now + getRandomNum(6);

        //此处需要一个逻辑去判断订单编号是否重复
        return orderSn;
    }

    private String getRandomNum(Integer num) {
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < num; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    @Override
    public boolean cancelUnPaidOrder(long second) {
        // 这里不直接update是因为执行过程中有可能订单状态已经发生变化，而又没有锁单，会导致订单其实已支付又被系统取消
        List<OrderEntity> orders = query().eq("status", ShopConst.OrderStatusEnum.PLACED.value())
                .apply("place_time < DATE_SUB(NOW(), interval " + second + " second)")
                .list();
        for (OrderEntity order : orders) {
            // 更新状态
            update().set("status", ShopConst.OrderStatusEnum.CANCELED.value()).eq("id", order.getId()).update(new OrderEntity());
            // 插入日志
            OrderLogEntity orderLog = new OrderLogEntity();
            orderLog.setOrderId(order.getId());
            orderLog.setType(ShopConst.OrderStatusEnum.CANCELED.value());
            orderLog.setContent("定时任务取消超时未支付订单");
            orderLogService.save(orderLog);
        }
        return false;
    }

    @Override
    public boolean checkOrder(Long orderId) {
        update().eq("id", orderId).set("status", ShopConst.OrderStatusEnum.CANCELED.value()).update(new OrderEntity());
       /* OrderEntity orderEntity =query().eq("status", ShopConst.OrderStatusEnum.PLACED.value())
                .eq("id", orderId)
                .apply("place_time < DATE_SUB(NOW(), interval " + second + " second)")
                .one();*/

        return false;
    }

    @Override
    public boolean cancel(Long id) {
        update().eq("id", id).set("status", ShopConst.OrderStatusEnum.CANCELED.value()).update(new OrderEntity());
        return false;
    }
}
