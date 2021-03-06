package com.nb6868.xquick.modules.uc.service;

import com.nb6868.xquick.booster.service.BaseService;
import com.nb6868.xquick.modules.uc.entity.RoleUserEntity;

import java.util.List;

/**
 * 角色用户关系
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
public interface RoleUserService extends BaseService<RoleUserEntity> {

    /**
     * 保存或修改
     * @param userId      用户ID
     * @param roleIds  角色ID列表
     */
    boolean saveOrUpdate(Long userId, List<Long> roleIds);

    /**
     * 根据角色ids，删除角色用户关系
     * @param roleIds 角色ids
     */
    boolean deleteByRoleIds(List<Long> roleIds);

    /**
     * 根据用户id，删除角色用户关系
     * @param userIds 用户ids
     */
    boolean deleteByUserIds(List<Long> userIds);

    /**
     * 角色ID列表
     * @param userId  用户ID
     */
    List<Long> getRoleIdList(Long userId);
}
