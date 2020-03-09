package co.xquick.modules.uc.dao;

import co.xquick.booster.dao.BaseDao;
import co.xquick.modules.uc.entity.RoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 角色管理
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Mapper
public interface RoleDao extends BaseDao<RoleEntity> {

    /**
     * 查询所有角色列表
     */
    @Select("select code from uc_role where deleted = 0")
    List<String> getRoleList();

    /**
     * 根据用户id查询角色列表
     *
     * @param userId 用户ID
     */
    @Select("select uc_role.code from uc_role_user" +
            " left join uc_role on uc_role.id = uc_role_user.role_id" +
            " where uc_role.deleted = 0 and uc_role_user.deleted = 0 and uc_role_user.user_id = #{userId}")
    List<String> getRoleList(@Param("userId") Long userId);

}
