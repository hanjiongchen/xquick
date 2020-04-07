package co.xquick.modules.sys.dao;

import co.xquick.booster.dao.BaseDao;
import co.xquick.modules.sys.entity.AdEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 广告位
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Mapper
public interface AdDao extends BaseDao<AdEntity> {

}
