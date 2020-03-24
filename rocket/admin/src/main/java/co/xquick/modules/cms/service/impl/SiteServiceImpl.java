package co.xquick.modules.cms.service.impl;

import co.xquick.booster.exception.ErrorCode;
import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.booster.util.WrapperUtils;
import co.xquick.booster.validator.AssertUtils;
import co.xquick.modules.cms.dao.SiteDao;
import co.xquick.modules.cms.dto.SiteDTO;
import co.xquick.modules.cms.entity.SiteEntity;
import co.xquick.modules.cms.service.ArticleCategoryService;
import co.xquick.modules.cms.service.ArticleService;
import co.xquick.modules.cms.service.SiteService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 站点
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class SiteServiceImpl extends CrudServiceImpl<SiteDao, SiteEntity, SiteDTO> implements SiteService {

    @Autowired
    ArticleService articleService;
    @Autowired
    ArticleCategoryService articleCategoryService;

    @Override
    public QueryWrapper<SiteEntity> getWrapper(String method, Map<String, Object> params){
        return new WrapperUtils<SiteEntity>(new QueryWrapper<>(), params)
                .like("name", "name")
                .like("status", "status")
                .getQueryWrapper();
    }

    @Override
    protected void beforeSaveOrUpdateDto(SiteDTO dto, int type) {
        boolean hasRecord = hasDuplicated(dto.getId(), "code", dto.getCode());
        AssertUtils.isTrue(hasRecord, ErrorCode.HAS_DUPLICATED_RECORD, "编码");
    }

    @Override
    protected void afterSaveOrUpdateDto(boolean ret, SiteDTO dto, SiteEntity existedEntity, int type) {
        if (ret && type == 1) {
            // 更新成功
            if (!StringUtils.equals(existedEntity.getCode(), dto.getCode())) {
                // 如果code发生变化,更新相关业务表中的site_code
                articleCategoryService.updateSiteCode(dto.getId(), dto.getCode());
                articleService.updateSiteCode(dto.getId(), dto.getCode());
            }
        }
    }

}
