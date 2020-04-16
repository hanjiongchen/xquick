package com.nb6868.xquick.modules.shop.dto;

import com.nb6868.xquick.booster.pojo.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 订单
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "订单")
public class OrderPlaceRequest extends BaseDTO {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户id")
	private Long userId;

	@ApiModelProperty(value = "用户备注")
	private String userRemark;

	@ApiModelProperty(value = "支付类型 0 无须支付 1 现金交易 2 银行转账 3 支付宝支付 4 微信支付")
	private Integer payType;

	@ApiModelProperty(value = "收货地址id")
	private Long receiverId;

	@ApiModelProperty(value = "收件人")
	private String receiverConsignee;

	@ApiModelProperty(value = "收件人电话")
	private String receiverMobile;

	@ApiModelProperty(value = "收件详细地址")
	private String receiverAddress;

	@ApiModelProperty(value = "收件地址区域编码")
	private String receiverRegionCode;

	@ApiModelProperty(value = "收件地址区域")
	private String receiverRegionName;

	@ApiModelProperty(value = "商品明细")
	private List<OrderItemPlaceRequest> items;

}
