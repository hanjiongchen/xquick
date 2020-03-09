package co.xquick.modules.uc.dao;

import co.xquick.booster.dao.BaseDao;
import co.xquick.modules.uc.entity.RoleDataScopeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 角色数据权限
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Mapper
public interface RoleDataScopeDao extends BaseDao<RoleDataScopeEntity> {

    /**
     * 根据角色ID，获取部门ID列表
     */
    @Select("select dept_id from uc_role_data_scope where deleted = 0 and role_id = #{roleId}")
    List<Long> getDeptIdList(@Param("roleId") Long roleId);

    /**
     * 获取用户的部门数据权限列表
     */
    @Select("select t2.dept_id from uc_role_user t1, uc_role_data_scope t2 where t1.deleted = 0 and t2.deleted = 0 and t1.user_id = #{userId} and t1.role_id = t2.role_id")
    List<Long> getDataScopeList(@Param("userId") Long userId);

}
