package co.xquick.modules.shop.service;

import co.xquick.booster.service.CrudService;
import co.xquick.modules.shop.dto.SpuCategoryDTO;
import co.xquick.modules.shop.dto.SpuCategoryTreeDTO;
import co.xquick.modules.shop.entity.SpuCategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品类别
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public interface SpuCategoryService extends CrudService<SpuCategoryEntity, SpuCategoryDTO> {
    /**
     * 树状列表
     */
    List<SpuCategoryTreeDTO> tree(Map<String, Object> params);

    /**
     * 递归上级菜单列表
     *
     * @param id 类别id
     */
    List<SpuCategoryDTO> getParentMenuList(Long id);

}
