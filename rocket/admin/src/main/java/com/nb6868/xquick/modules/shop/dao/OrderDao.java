package com.nb6868.xquick.modules.shop.dao;

import com.nb6868.xquick.booster.dao.BaseDao;
import com.nb6868.xquick.modules.shop.entity.OrderEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Mapper
public interface OrderDao extends BaseDao<OrderEntity> {

}
