package co.xquick.modules.shop.service;

import co.xquick.booster.service.CrudService;
import co.xquick.modules.shop.dto.CategoryDTO;
import co.xquick.modules.shop.entity.CategoryEntity;

import java.util.List;

/**
 * 商品类别
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public interface CategoryService extends CrudService<CategoryEntity, CategoryDTO> {
    /**
     * 菜单列表
     *
     */
    public List<CategoryDTO> getAllMenuList();

    /**
     * 递归上级菜单列表
     *
     * @param id 菜单ID
     */
    List<CategoryDTO> getParentMenuList(Long id);
}
