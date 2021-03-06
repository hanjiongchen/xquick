package com.nb6868.xquick.modules.shop.dto;

import com.nb6868.xquick.booster.pojo.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 购物车
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "购物车")
public class CartDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "会员id")
	private Long userId;

	@ApiModelProperty(value = "spu id")
	private Long spuId;

	@ApiModelProperty(value = "sku id")
	private Long skuId;

	@ApiModelProperty(value = "商铺id")
	private Long storeId;

	@ApiModelProperty(value = "数量")
	private BigDecimal qty;

	@ApiModelProperty(value = "状态0 未下单 1 已下单")
	private Integer status;

}
