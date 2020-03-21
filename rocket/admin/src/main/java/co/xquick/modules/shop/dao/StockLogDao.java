package co.xquick.modules.shop.dao;

import co.xquick.booster.dao.BaseDao;
import co.xquick.modules.shop.entity.StockLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 出入库记录
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Mapper
public interface StockLogDao extends BaseDao<StockLogEntity> {

}
