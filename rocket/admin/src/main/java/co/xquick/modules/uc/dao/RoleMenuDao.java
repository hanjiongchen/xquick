package co.xquick.modules.uc.dao;

import co.xquick.booster.dao.BaseDao;
import co.xquick.modules.uc.entity.RoleMenuEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色与菜单对应关系
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Mapper
public interface RoleMenuDao extends BaseDao<RoleMenuEntity> {

}
