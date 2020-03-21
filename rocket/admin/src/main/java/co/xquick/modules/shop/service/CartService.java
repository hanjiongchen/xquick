package co.xquick.modules.shop.service;

import co.xquick.booster.service.CrudService;
import co.xquick.modules.shop.dto.CartDTO;
import co.xquick.modules.shop.entity.CartEntity;

/**
 * 购物车
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public interface CartService extends CrudService<CartEntity, CartDTO> {

}
