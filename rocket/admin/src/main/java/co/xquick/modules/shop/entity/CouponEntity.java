package co.xquick.modules.shop.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import co.xquick.booster.pojo.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 优惠券
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("shop_coupon")
public class CouponEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

    /**
     * 商铺id
     */
	private Long storeId;
    /**
     * 名称
     */
	private String name;
    /**
     * 描述
     */
	private String content;
    /**
     * 类型
     */
	private Integer type;
    /**
     * 有效期开始
     */
	private Date validStartTime;
    /**
     * 有效期结束
     */
	private Date validEndTime;
    /**
     * 状态 0 未激活 1 已激活
     */
	private Integer status;
    /**
     * 是否可以积分兑换
     */
	private Integer pointExchangeEnable;
    /**
     * 兑换积分
     */
	private Integer pointExchange;
    /**
     * 当前数量
     */
	private Integer stock;
    /**
     * 最大商品价格
     */
	private BigDecimal maxPrice;
    /**
     * 最大sku数量
     */
	private Integer maxQty;
    /**
     * 最小商品价格
     */
	private BigDecimal minPrice;
    /**
     * 最小sku数量
     */
	private Integer minQty;
    /**
     * 价格计算表达式
     */
	private String priceExpress;
	/**
	 * 用户名
	 */
	@TableField(exist = false)
	private String storeName;
}
