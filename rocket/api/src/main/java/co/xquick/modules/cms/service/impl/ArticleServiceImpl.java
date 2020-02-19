package co.xquick.modules.cms.service.impl;

import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.booster.util.ConvertUtils;
import co.xquick.booster.util.WrapperUtils;
import co.xquick.modules.cms.dao.ArticleDao;
import co.xquick.modules.cms.dto.ArticleDTO;
import co.xquick.modules.cms.entity.ArticleEntity;
import co.xquick.modules.cms.service.ArticleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 文章
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class ArticleServiceImpl extends CrudServiceImpl<ArticleDao, ArticleEntity, ArticleDTO> implements ArticleService {

    @Override
    public QueryWrapper<ArticleEntity> getWrapper(String method, Map<String, Object> params) {
        return new WrapperUtils<ArticleEntity>(new QueryWrapper<>(), params)
                .like("cms_article.author", "author")
                .like("cms_article.name", "name")
                .like("cms_article.content", "content")
                .eq("cms_article.article_category_id", "articleCategoryId")
                .eq("cms_article.top", "top")
                .eq("cms_article.status", "status")
                .getQueryWrapper()
                .eq("cms_article.deleted", 0)
                .orderByAsc("sort");
    }

    @Override
    public List<ArticleDTO> getListByCategoryId(Long categoryId) {
        QueryWrapper<ArticleEntity> wrapper = new QueryWrapper<ArticleEntity>()
                .eq("article_category_id", categoryId);

        return ConvertUtils.sourceToTarget(baseMapper.selectList(wrapper), ArticleDTO.class);
    }

    @Override
    public List<ArticleDTO> getListByCategoryIds(List<Long> categoryIds) {
        QueryWrapper<ArticleEntity> wrapper = new QueryWrapper<ArticleEntity>()
                .in("article_category_id", categoryIds);

        return ConvertUtils.sourceToTarget(baseMapper.selectList(wrapper), ArticleDTO.class);
    }

}
