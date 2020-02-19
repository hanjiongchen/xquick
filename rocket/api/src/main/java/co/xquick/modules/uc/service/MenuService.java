package co.xquick.modules.uc.service;

import co.xquick.booster.service.CrudService;
import co.xquick.modules.uc.dto.MenuTreeDTO;
import co.xquick.modules.uc.entity.MenuEntity;
import co.xquick.modules.uc.user.UserDetail;

import java.util.List;

/**
 * 菜单管理
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
public interface MenuService extends CrudService<MenuEntity, MenuTreeDTO> {

    /**
     * 递归上级菜单列表
     *
     * @param id 菜单ID
     */
    List<MenuTreeDTO> getParentMenuList(Long id);
    /**
     * 菜单列表
     *
     * @param type 菜单类型
     */
    List<MenuTreeDTO> getAllMenuList(Integer type);

    /**
     * 用户菜单列表
     *
     * @param user 用户
     * @param type 菜单类型
     */
    List<MenuTreeDTO> getUserMenuList(UserDetail user, Integer type);

    /**
     * 根据父菜单，查询子菜单
     *
     * @param pid 父菜单ID
     */
    List<MenuTreeDTO> getListPid(Long pid);
}
