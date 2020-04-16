package com.nb6868.xquick.modules.shop.dao;

import com.nb6868.xquick.booster.dao.BaseDao;
import com.nb6868.xquick.modules.shop.entity.CartEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 购物车
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Mapper
public interface CartDao extends BaseDao<CartEntity> {

}
