package co.xquick.modules.uc.service;

import co.xquick.modules.uc.entity.TokenEntity;
import co.xquick.modules.uc.entity.UserEntity;
import co.xquick.modules.uc.user.UserDetail;

import java.util.List;
import java.util.Set;

/**
 * shiro相关接口
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
public interface ShiroService {

    /**
     * 获取用户权限列表
     */
    Set<String> getUserPermissions(UserDetail user);

    /**
     * 获取用户角色列表
     */
    Set<String> getUserRoles(UserDetail user);

    TokenEntity getByToken(String token);

    /**
     * 续token的过期时间
     * @param token
     */
    void renewalToken(TokenEntity token);

    /**
     * 根据用户ID，查询用户
     * @param userId
     */
    UserEntity getUser(Long userId);

    /**
     * 获取用户对应的部门数据权限
     * @param userId  用户ID
     * @return        返回部门ID列表
     */
    List<Long> getDataScopeList(Long userId);

}