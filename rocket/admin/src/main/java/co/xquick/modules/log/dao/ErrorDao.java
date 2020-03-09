package co.xquick.modules.log.dao;

import co.xquick.booster.dao.BaseDao;
import co.xquick.modules.log.entity.ErrorEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 异常日志
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Mapper
public interface ErrorDao extends BaseDao<ErrorEntity> {

}
