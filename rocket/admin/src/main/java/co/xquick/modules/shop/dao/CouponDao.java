package co.xquick.modules.shop.dao;

import co.xquick.booster.dao.BaseDao;
import co.xquick.booster.pojo.Const;
import co.xquick.modules.shop.entity.CouponEntity;
import co.xquick.modules.shop.entity.ReceiverEntity;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 优惠券
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Mapper
public interface CouponDao extends BaseDao<CouponEntity> {
    @Select("SELECT shop_coupon.*, shop_store.name as store_name" +
            " FROM shop_coupon LEFT JOIN shop_store ON shop_coupon.store_id = shop_store.id" +
            " ${ew.customSqlSegment}")
    @Override
    <E extends IPage<CouponEntity>> E selectPage(@Param(Const.PAGE) E page, Wrapper<CouponEntity> ew);
}
