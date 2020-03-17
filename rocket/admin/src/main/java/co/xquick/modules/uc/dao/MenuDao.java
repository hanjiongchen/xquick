package co.xquick.modules.uc.dao;

import co.xquick.booster.dao.BaseDao;
import co.xquick.modules.uc.entity.MenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 菜单管理
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Mapper
public interface MenuDao extends BaseDao<MenuEntity> {

    @Select("select uc_menu.*, (select name from uc_menu parent where parent.deleted = 0 and parent.id = uc_menu.pid) as parent_name from uc_menu where uc_menu.deleted = 0 and uc_menu.id = #{id}")
    MenuEntity getById(@Param("id") Long id);

    /**
     * 查询所有菜单列表
     *
     * @param type 菜单类型
     */
    @Select("<script>" +
            "select t1.* from uc_menu t1" +
            " <where> t1.deleted = 0" +
            " <if test='type != null'>" +
            "  and t1.type = #{type}" +
            " </if>" +
            " </where>" +
            " order by t1.sort asc" +
            "</script>")
    List<MenuEntity> getMenuList(@Param("type") Integer type);

    /**
     * 查询用户菜单列表
     *
     * @param userId 用户ID
     * @param type   菜单类型
     */
    @Select("<script>" +
            "select t3.* from uc_role_user t1" +
            " left join uc_role_menu t2 on t1.role_id = t2.role_id" +
            " left join uc_menu t3 on t2.menu_id = t3.id" +
            " where t1.deleted = 0 and t2.deleted = 0 and t3.deleted = 0 and t1.user_id = #{userId}" +
            " <if test='type != null'>" +
            " and t3.type = #{type}" +
            " </if>" +
            " order by t3.sort asc" +
            "</script>")
    List<MenuEntity> getUserMenuList(@Param("userId") Long userId, @Param("type") Integer type);

    /**
     * 查询用户权限列表
     *
     * @param userId 用户ID
     */
    @Select("select t3.permissions from uc_role_user t1" +
            " left join uc_role_menu t2 on t1.role_id = t2.role_id" +
            " left join uc_menu t3 on t2.menu_id = t3.id" +
            " where t1.deleted = 0 and t2.deleted = 0 and t3.deleted = 0 and t3.permissions != '' and t1.user_id = #{userId} " +
            " order by t3.sort asc")
    List<String> getUserPermissionsList(@Param("userId") Long userId);

    /**
     * 查询所有权限列表
     */
    @Select("select permissions from uc_menu where permissions != '' and deleted = 0")
    List<String> getPermissionsList();

    /**
     * 根据父菜单，查询子菜单
     *
     * @param pid 父菜单ID
     */
    @Select("select * from uc_menu where deleted = 0 and pid = #{pid}")
    List<MenuEntity> getListPid(@Param("pid") Long pid);

}
