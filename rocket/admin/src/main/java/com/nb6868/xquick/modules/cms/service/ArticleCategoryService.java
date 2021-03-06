package com.nb6868.xquick.modules.cms.service;

import com.nb6868.xquick.booster.service.CrudService;
import com.nb6868.xquick.modules.cms.dto.ArticleCategoryDTO;
import com.nb6868.xquick.modules.cms.entity.ArticleCategoryEntity;

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
