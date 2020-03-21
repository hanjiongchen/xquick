package co.xquick.modules.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.modules.shop.dao.CategoryDao;
import co.xquick.modules.shop.dto.CategoryDTO;
import co.xquick.modules.shop.entity.CategoryEntity;
import co.xquick.modules.shop.service.CategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

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
        String id = (String)params.get("id");

        QueryWrapper<CategoryEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }

}
