package co.xquick.modules.uc.service;

import co.xquick.booster.service.CrudService;
import co.xquick.modules.uc.dto.RoleDTO;
import co.xquick.modules.uc.entity.RoleEntity;

import java.util.List;

/**
 * 角色
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
public interface RoleService extends CrudService<RoleEntity, RoleDTO> {

    /**
     * 查询所有角色列表
     */
    List<String> getRoleList();

    /**
     * 根据用户查询角色列表
     *
     * @param userId 用户id
     */
    List<String> getRoleListByUserId(Long userId);

}
