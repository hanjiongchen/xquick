package co.xquick.modules.shop.service.impl;

import co.xquick.booster.exception.XquickException;
import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.booster.util.ConvertUtils;
import co.xquick.booster.util.TreeUtils;
import co.xquick.booster.util.WrapperUtils;
import co.xquick.modules.shop.dao.CategoryDao;
import co.xquick.modules.shop.dto.CategoryDTO;
import co.xquick.modules.shop.dto.CategoryTreeDTO;
import co.xquick.modules.shop.entity.CategoryEntity;
import co.xquick.modules.shop.service.CategoryService;
import co.xquick.modules.shop.service.SpuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
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

    @Autowired
    private SpuService spuService;

    @Override
    public QueryWrapper<CategoryEntity> getWrapper(String method, Map<String, Object> params) {
        return new WrapperUtils<CategoryEntity>(new QueryWrapper<>(), params)
                .like("name", "name")
                .eq("pid", "pid")
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
    public boolean logicDeleteById(Serializable id) {
        if (query().eq("pid", id).count() > 0) {
            throw new XquickException("存在子类别,不允许删除");
        }
        if (spuService.query().eq("category_id", id).count() > 0) {
            throw new XquickException("该类别下存在商品,不允许删除");
        }
        return super.logicDeleteById(id);
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
