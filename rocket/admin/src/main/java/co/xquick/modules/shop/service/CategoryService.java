package co.xquick.modules.shop.service;

import co.xquick.booster.service.CrudService;
import co.xquick.modules.shop.dto.CategoryDTO;
import co.xquick.modules.shop.dto.CategoryTreeDTO;
import co.xquick.modules.shop.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品类别
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public interface CategoryService extends CrudService<CategoryEntity, CategoryDTO> {
    /**
     * 树状列表
     */
    List<CategoryTreeDTO> tree(Map<String, Object> params);

    /**
     * 递归上级菜单列表
     *
     * @param id 类别id
     */
    List<CategoryDTO> getParentMenuList(Long id);

}
