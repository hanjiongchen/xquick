package co.xquick.modules.cms.dao;

import co.xquick.booster.constant.Constant;
import co.xquick.booster.dao.BaseDao;
import co.xquick.modules.cms.entity.ArticleCategoryEntity;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 文章分类
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Mapper
public interface ArticleCategoryDao extends BaseDao<ArticleCategoryEntity> {

    @Select("SELECT cms_article_category.*, cms_site.name as site_name" +
            " FROM cms_article_category LEFT JOIN cms_site ON cms_article_category.site_id = cms_site.id" +
            " ${ew.customSqlSegment}")
    @Override
    <E extends IPage<ArticleCategoryEntity>> E selectPage(@Param(Constant.PAGE) E page, Wrapper<ArticleCategoryEntity> ew);

}
