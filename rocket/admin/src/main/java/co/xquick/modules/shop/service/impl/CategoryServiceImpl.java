package co.xquick.modules.shop.service.impl;

import co.xquick.booster.constant.Constant;
import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.booster.util.ConvertUtils;
import co.xquick.booster.util.TreeUtils;
import co.xquick.modules.shop.dao.CategoryDao;
import co.xquick.modules.shop.dto.CategoryDTO;
import co.xquick.modules.shop.entity.CategoryEntity;
import co.xquick.modules.shop.service.CategoryService;
import co.xquick.modules.uc.dto.MenuTreeDTO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
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
    public QueryWrapper<CategoryEntity> getWrapper(String method, Map<String, Object> params){
        String name = (String)params.get("name");

        QueryWrapper<CategoryEntity> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(name), "name", name);

        return wrapper;
    }

    @Override
    public List<CategoryDTO> getAllMenuList() {
        List<CategoryEntity> categoryList = baseMapper.getCategoryList();

        List<CategoryDTO> dtoList = ConvertUtils.sourceToTarget(categoryList, CategoryDTO.class);

        return TreeUtils.build(dtoList, Constant.MENU_ROOT);
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
