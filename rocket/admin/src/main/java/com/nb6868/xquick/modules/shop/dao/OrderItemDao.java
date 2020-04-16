package com.nb6868.xquick.modules.shop.dao;

import com.nb6868.xquick.booster.dao.BaseDao;
import com.nb6868.xquick.modules.shop.entity.OrderItemEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单明细
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Mapper
public interface OrderItemDao extends BaseDao<OrderItemEntity> {

}
