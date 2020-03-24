package co.xquick.modules.cms.service.impl;

import co.xquick.booster.exception.ErrorCode;
import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.booster.util.ConvertUtils;
import co.xquick.booster.util.WrapperUtils;
import co.xquick.booster.validator.AssertUtils;
import co.xquick.modules.cms.dao.ArticleDao;
import co.xquick.modules.cms.dto.ArticleDTO;
import co.xquick.modules.cms.entity.ArticleEntity;
import co.xquick.modules.cms.service.ArticleCategoryService;
import co.xquick.modules.cms.service.ArticleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    ArticleCategoryService articleCategoryService;

    @Override
    public QueryWrapper<ArticleEntity> getWrapper(String method, Map<String, Object> params) {
        return new WrapperUtils<ArticleEntity>(new QueryWrapper<>(), params)
                .like("author", "cms_article.author")
                .like("name", "cms_article.name")
                .like("content", "cms_article.content")
                .eq("articleCategoryId", "cms_article.article_category_id")
                .eq("articleCategoryCode", "cms_article.article_category_code")
                .eq("top", "cms_article.top")
                .eq("status", "cms_article.status")
                .eq("siteId", "cms_article.site_id")
                .eq("siteCode", "cms_article.site_code")
                .getQueryWrapper()
                .eq("cms_article.deleted", 0)
                .orderByAsc("sort");
    }

    /*@Override
    public PageData<ArticleDTO> pageDto(Map<String, Object> params) {
        PageData<ArticleDTO> page = super.pageDto(params);
        page.getList().forEach(dto -> {
            String categoryName = articleCategoryService.query().select("name").eq("id", dto.getArticleCategoryId()).last("limit 1").one().getName();
            dto.setArticleCategoryName(categoryName);
        });

        return page;
    }*/

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

    @Override
    protected void beforeSaveOrUpdateDto(ArticleDTO dto, int type) {
        boolean hasRecord = hasRecord(new QueryWrapper<ArticleEntity>().eq(StringUtils.isNotBlank(dto.getCode()), "code", dto.getCode())
                .eq("site_id", dto.getSiteId())
                .ne(dto.getId() != null, "id", dto.getId()));
        AssertUtils.isTrue(hasRecord, ErrorCode.HAS_DUPLICATED_RECORD, "编码");
    }

    @Override
    public boolean updateSiteCode(Long siteId, String newSiteCode) {
        return update(new UpdateWrapper<ArticleEntity>().eq("site_id", siteId).set("site_code", newSiteCode));
    }

    @Override
    public boolean updateArticleCategoryCode(Long siteId, String newSiteCode) {
        return update(new UpdateWrapper<ArticleEntity>().eq("article_category_id", siteId).set("article_category_code", newSiteCode));
    }

}
