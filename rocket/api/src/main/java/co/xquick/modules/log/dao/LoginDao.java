package co.xquick.modules.log.dao;

import co.xquick.booster.dao.BaseDao;
import co.xquick.modules.log.entity.LoginEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 登录日志
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Mapper
public interface LoginDao extends BaseDao<LoginEntity> {

}
