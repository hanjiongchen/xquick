package co.xquick.modules.cms.service;

import co.xquick.booster.service.CrudService;
import co.xquick.modules.cms.dto.ArticleDTO;
import co.xquick.modules.cms.entity.ArticleEntity;

import java.util.List;

/**
 * 文章
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public interface ArticleService extends CrudService<ArticleEntity, ArticleDTO> {

    /**
     * 根据类别，查询文章
     *
     * @param categoryId 文章分类
     */
    List<ArticleDTO> getListByCategoryId(Long categoryId);

    /**
     * 根据类别，查询文章
     *
     * @param categoryIds 文章分类
     */
    List<ArticleDTO> getListByCategoryIds(List<Long> categoryIds);

    /**
     * 更新sitecode
     */
    boolean updateSiteCode(Long siteId, String newSiteCode);

    /**
     * 更新articleCategoryCode
     */
    boolean updateArticleCategoryCode(Long articleCategoryId, String articleCategoryCode);
}
