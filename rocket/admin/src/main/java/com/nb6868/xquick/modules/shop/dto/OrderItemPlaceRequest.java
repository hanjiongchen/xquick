package com.nb6868.xquick.modules.shop.dto;

import com.nb6868.xquick.booster.pojo.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 订单
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "订单")
public class OrderItemPlaceRequest extends BaseDTO {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "skuId")
	private Long skuId;

	@ApiModelProperty(value = "skuId")
	private Long spuId;

	@ApiModelProperty(value = "数量")
	private BigDecimal qty;

}
