package co.xquick.modules.uc.dao;

import co.xquick.booster.dao.BaseDao;
import co.xquick.modules.uc.entity.RoleMenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 角色与菜单对应关系
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Mapper
public interface RoleMenuDao extends BaseDao<RoleMenuEntity> {

	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	@Select("select menu_id from uc_role_menu where deleted = 0 and role_id = #{roleId}")
	List<Long> getMenuIdList(@Param("roleId") Long roleId);

}
