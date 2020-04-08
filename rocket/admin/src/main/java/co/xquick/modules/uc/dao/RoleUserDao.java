package co.xquick.modules.uc.dao;

import co.xquick.booster.dao.BaseDao;
import co.xquick.modules.uc.entity.RoleUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 角色用户关系
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Mapper
public interface RoleUserDao extends BaseDao<RoleUserEntity> {

}
