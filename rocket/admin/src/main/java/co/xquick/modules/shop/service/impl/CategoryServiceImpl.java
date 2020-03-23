package co.xquick.modules.shop.service.impl;

import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.booster.util.ConvertUtils;
import co.xquick.booster.util.TreeUtils;
import co.xquick.booster.util.WrapperUtils;
import co.xquick.modules.shop.dao.CategoryDao;
import co.xquick.modules.shop.dto.CategoryDTO;
import co.xquick.modules.shop.dto.CategoryTreeDTO;
import co.xquick.modules.shop.entity.CategoryEntity;
import co.xquick.modules.shop.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 商品类别
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class CategoryServiceImpl extends CrudServiceImpl<CategoryDao, CategoryEntity, CategoryDTO> implements CategoryService {

    @Override
    public QueryWrapper<CategoryEntity> getWrapper(String method, Map<String, Object> params) {
        return new WrapperUtils<CategoryEntity>(new QueryWrapper<>(), params)
                .like("name", "name")
                .getQueryWrapper()
                .orderByAsc("sort");
    }

    @Override
    public List<CategoryTreeDTO> tree(Map<String, Object> params) {
        List<CategoryEntity> entityList = getBaseMapper().selectList(getWrapper("tree", params));

        List<CategoryTreeDTO> dtoList = ConvertUtils.sourceToTarget(entityList, CategoryTreeDTO.class);

        return TreeUtils.build(dtoList);
    }

    @Override
    public List<CategoryDTO> getParentMenuList(Long id) {
        List<CategoryDTO> menus = new ArrayList<>();
        while (id != 0) {
            CategoryDTO dto = getDtoById(id);
            if (dto != null) {
                menus.add(dto);
                id = dto.getPid();
            } else {
                id = 0L;
            }
        }
        Collections.reverse(menus);
        return menus;
    }
}
