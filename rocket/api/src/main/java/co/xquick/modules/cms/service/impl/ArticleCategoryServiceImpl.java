package co.xquick.modules.cms.service.impl;

import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.booster.util.ParamUtils;
import co.xquick.modules.cms.dao.ArticleCategoryDao;
import co.xquick.modules.cms.dto.ArticleCategoryDTO;
import co.xquick.modules.cms.entity.ArticleCategoryEntity;
import co.xquick.modules.cms.service.ArticleCategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 文章分类
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class ArticleCategoryServiceImpl extends CrudServiceImpl<ArticleCategoryDao, ArticleCategoryEntity, ArticleCategoryDTO> implements ArticleCategoryService {

    @Override
    public QueryWrapper<ArticleCategoryEntity> getWrapper(String method, Map<String, Object> params){
        return new QueryWrapper<ArticleCategoryEntity>()
                .like(ParamUtils.isNotEmpty(params.get("name")), "name", params.get("name"))
                .orderByAsc("sort");
    }

}
