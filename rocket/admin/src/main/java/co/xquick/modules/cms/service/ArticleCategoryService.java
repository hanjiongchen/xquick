package co.xquick.modules.cms.service;

import co.xquick.booster.service.CrudService;
import co.xquick.modules.cms.dto.ArticleCategoryDTO;
import co.xquick.modules.cms.entity.ArticleCategoryEntity;

/**
 * 文章分类
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public interface ArticleCategoryService extends CrudService<ArticleCategoryEntity, ArticleCategoryDTO> {

    /**
     * 更新sitecode
     */
    boolean updateSiteCode(Long siteId, String newSiteCode);

}
