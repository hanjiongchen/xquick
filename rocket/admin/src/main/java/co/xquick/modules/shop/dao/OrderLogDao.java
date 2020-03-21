package co.xquick.modules.shop.dao;

import co.xquick.booster.dao.BaseDao;
import co.xquick.modules.shop.entity.OrderLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单记录
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Mapper
public interface OrderLogDao extends BaseDao<OrderLogEntity> {

}
