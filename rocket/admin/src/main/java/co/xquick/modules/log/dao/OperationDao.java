package co.xquick.modules.log.dao;

import co.xquick.booster.dao.BaseDao;
import co.xquick.modules.log.entity.OperationEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作日志
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Mapper
public interface OperationDao extends BaseDao<OperationEntity> {

}
