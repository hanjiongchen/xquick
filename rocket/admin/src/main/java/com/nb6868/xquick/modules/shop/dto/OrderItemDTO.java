package com.nb6868.xquick.modules.shop.dto;

import com.nb6868.xquick.booster.pojo.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 订单明细
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "订单明细")
public class OrderItemDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "订单id")
	private Long orderId;

	@ApiModelProperty(value = "spu id")
	private Long spuId;

	@ApiModelProperty(value = "sku id")
	private Long skuId;

	@ApiModelProperty(value = "数量")
	private BigDecimal qty;

	@ApiModelProperty(value = "单价")
	private BigDecimal price;

	@ApiModelProperty(value = "实付费用")
	private BigDecimal payPrice;

	@ApiModelProperty(value = "spu名称")
	private String spuName;

	@ApiModelProperty(value = "sku名称")
	private String skuName;

	@ApiModelProperty(value = "spu封面")
	private String spuCover;

	@ApiModelProperty(value = "sku封面")
	private String skuCover;

}
