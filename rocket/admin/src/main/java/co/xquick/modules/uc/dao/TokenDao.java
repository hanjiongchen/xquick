package co.xquick.modules.uc.dao;

import co.xquick.booster.dao.BaseDao;
import co.xquick.modules.uc.entity.TokenEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 系统用户Token
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Mapper
public interface TokenDao extends BaseDao<TokenEntity> {

}
