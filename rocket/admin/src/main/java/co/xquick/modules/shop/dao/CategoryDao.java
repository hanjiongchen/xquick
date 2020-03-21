package co.xquick.modules.shop.dao;

import co.xquick.booster.dao.BaseDao;
import co.xquick.modules.shop.entity.CategoryEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品类别
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Mapper
public interface CategoryDao extends BaseDao<CategoryEntity> {

}
