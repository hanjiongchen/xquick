package co.xquick.modules.shop.dao;

import co.xquick.booster.dao.BaseDao;
import co.xquick.modules.shop.entity.OrderEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Mapper
public interface OrderDao extends BaseDao<OrderEntity> {

}
