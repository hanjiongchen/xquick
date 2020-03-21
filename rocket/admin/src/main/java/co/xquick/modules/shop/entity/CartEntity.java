package co.xquick.modules.shop.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import co.xquick.booster.pojo.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 购物车
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("shop_cart")
public class CartEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

    /**
     * 会员id
     */
	private Long userId;
    /**
     * spu id
     */
	private Long spuId;
    /**
     * sku id
     */
	private Long skuId;
    /**
     * 商铺id
     */
	private Long storeId;
    /**
     * 数量
     */
	private Integer qty;
    /**
     * 状态0 未下单 1 已下单
     */
	private Integer status;
}
