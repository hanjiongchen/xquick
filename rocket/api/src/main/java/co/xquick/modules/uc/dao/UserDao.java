package co.xquick.modules.uc.dao;

import co.xquick.booster.dao.BaseDao;
import co.xquick.modules.uc.entity.UserEntity;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;
import java.util.List;

/**
 * 用户
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Mapper
public interface UserDao extends BaseDao<UserEntity> {

    /**select user with role*/
    String WITH_ROLE_SQL = "select uc_user.*, " +
            "(SELECT uc_dept.name FROM uc_dept WHERE uc_dept.deleted = 0 AND uc_dept.id = uc_user.dept_id) dept_name, role.role_ids, role.role_names" +
            " FROM uc_user" +
            " LEFT JOIN (SELECT GROUP_CONCAT(uc_role_user.role_id) AS role_ids, uc_role_user.user_id, GROUP_CONCAT(uc_role.name) AS role_names FROM uc_role_user LEFT JOIN uc_role ON uc_role_user.role_id = uc_role.id WHERE uc_role_user.deleted = 0 AND uc_role.deleted = 0 GROUP BY uc_role_user.user_id) as role ON role.user_id = uc_user.id" +
            " ${ew.customSqlSegment}";

    @Select(WITH_ROLE_SQL)
    IPage<UserEntity> selectWithRolePage(IPage<UserEntity> page, @Param(Constants.WRAPPER) Wrapper<UserEntity> ew);

    @Select(WITH_ROLE_SQL)
    List<UserEntity> selectWithRoleList(@Param(Constants.WRAPPER) Wrapper<UserEntity> ew);

    @Override
    @Select("select uc_user.*, (select uc_dept.name from uc_dept where uc_dept.deleted = 0 and uc_dept.id = uc_user.dept_id) dept_name from uc_user where uc_user.deleted = 0 and uc_user.id = #{id}")
    UserEntity selectById(@Param("id") Serializable id);

    @Select("select * from uc_user where deleted = 0 and username = #{username}")
    UserEntity getByUsername(@Param("username") String username);

    @Select("select * from uc_user where deleted = 0 and mobile = #{mobile}")
    UserEntity getByMobile(@Param("mobile") String mobile);

}
