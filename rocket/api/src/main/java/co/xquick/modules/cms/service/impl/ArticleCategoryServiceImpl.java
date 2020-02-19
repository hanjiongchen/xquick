package co.xquick.modules.cms.service.impl;

import co.xquick.booster.exception.ErrorCode;
import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.booster.util.ConvertUtils;
import co.xquick.booster.util.WrapperUtils;
import co.xquick.booster.validator.AssertUtils;
import co.xquick.modules.cms.dao.ArticleCategoryDao;
import co.xquick.modules.cms.dto.ArticleCategoryDTO;
import co.xquick.modules.cms.entity.ArticleCategoryEntity;
import co.xquick.modules.cms.service.ArticleCategoryService;
import co.xquick.modules.cms.service.ArticleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 文章分类
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class ArticleCategoryServiceImpl extends CrudServiceImpl<ArticleCategoryDao, ArticleCategoryEntity, ArticleCategoryDTO> implements ArticleCategoryService {

    @Autowired
    ArticleService articleService;

    @Override
    public QueryWrapper<ArticleCategoryEntity> getWrapper(String method, Map<String, Object> params){
        return new WrapperUtils<ArticleCategoryEntity>(new QueryWrapper<>(), params)
                .like("cms_article_category.name", "name")
                .like("cms_article_category.sort", "sort")
                .like("cms_article_category.code", "code")
                .eq("cms_article_category.site_id", "siteId")
                .eq("cms_article_category.site_code", "siteCode")
                .getQueryWrapper()
                .orderByAsc("cms_article_category.sort");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOrUpdateDto(ArticleCategoryDTO dto) {
        // 检查code是否存在
        boolean hasRecord = hasRecord(new QueryWrapper<ArticleCategoryEntity>().eq(StringUtils.isNotBlank(dto.getCode()), "code", dto.getCode())
                .eq("site_id", dto.getSiteId())
                .ne(dto.getId() != null, "id", dto.getId()));
        AssertUtils.isTrue(hasRecord, ErrorCode.HAS_DUPLICATED_RECORD, "编码");

        ArticleCategoryEntity entity = ConvertUtils.sourceToTarget(dto, currentModelClass());
        if (hasIdVal(entity)) {
            ArticleCategoryEntity existedEntity = getById(entity.getId());
            AssertUtils.isNull(existedEntity, ErrorCode.DB_RECORD_NOT_EXISTED);

            boolean ret = updateById(entity);
            if (ret && !StringUtils.equals(existedEntity.getCode(), dto.getCode())) {
                // 如果code发生变化,更新相关业务表中的code
                articleService.updateArticleCategoryCode(dto.getId(), dto.getCode());
            }
            return ret;
        } else {
            boolean ret = save(entity);
            // copy主键值到dto
            BeanUtils.copyProperties(entity, dto);
            return ret;
        }
    }

    @Override
    public boolean updateSiteCode(Long siteId, String newSiteCode) {
        return update(new UpdateWrapper<ArticleCategoryEntity>().eq("site_id", siteId).set("site_code", newSiteCode));
    }

}
