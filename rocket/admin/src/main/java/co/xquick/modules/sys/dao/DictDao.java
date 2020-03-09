package co.xquick.modules.sys.dao;

import co.xquick.booster.dao.BaseDao;
import co.xquick.modules.sys.entity.DictEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据字典
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Mapper
public interface DictDao extends BaseDao<DictEntity> {

}
