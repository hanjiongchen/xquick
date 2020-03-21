package co.xquick.modules.shop.dto;

import co.xquick.booster.pojo.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

import java.math.BigDecimal;

/**
 * 优惠券
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "优惠券")
public class CouponDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "商铺id")
	private Long storeId;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "描述")
	private String content;

	@ApiModelProperty(value = "类型")
	private Integer type;

	@ApiModelProperty(value = "有效期开始")
	private Date validStartTime;

	@ApiModelProperty(value = "有效期结束")
	private Date validEndTime;

	@ApiModelProperty(value = "状态 0 未激活 1 已激活")
	private Integer status;

	@ApiModelProperty(value = "是否可以积分兑换")
	private Integer pointExchangeEnable;

	@ApiModelProperty(value = "兑换积分")
	private Integer pointExchange;

	@ApiModelProperty(value = "当前数量")
	private Integer stock;

	@ApiModelProperty(value = "最大商品价格")
	private BigDecimal maxPrice;

	@ApiModelProperty(value = "最大sku数量")
	private Integer maxQty;

	@ApiModelProperty(value = "最小商品价格")
	private BigDecimal minPrice;

	@ApiModelProperty(value = "最小sku数量")
	private Integer minQty;

	@ApiModelProperty(value = "价格计算表达式")
	private String priceExpress;

}
