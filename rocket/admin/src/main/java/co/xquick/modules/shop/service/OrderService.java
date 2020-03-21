package co.xquick.modules.shop.service;

import co.xquick.booster.service.CrudService;
import co.xquick.modules.shop.dto.OrderDTO;
import co.xquick.modules.shop.entity.OrderEntity;

/**
 * 订单
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public interface OrderService extends CrudService<OrderEntity, OrderDTO> {

}
