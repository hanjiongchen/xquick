package com.nb6868.xquick.modules.shop.dao;

import com.nb6868.xquick.booster.dao.BaseDao;
import com.nb6868.xquick.modules.shop.entity.StockLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 出入库记录
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Mapper
public interface StockLogDao extends BaseDao<StockLogEntity> {

}
