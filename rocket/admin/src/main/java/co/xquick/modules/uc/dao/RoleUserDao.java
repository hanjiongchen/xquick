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

    /**
     * 角色ID列表
     *
     * @param userId 用户ID
     * @return
     */
    @Select("select role_id from uc_role_user where deleted = 0 and user_id = #{userId}")
    List<Long> getRoleIdList(@Param("userId") Long userId);
}
