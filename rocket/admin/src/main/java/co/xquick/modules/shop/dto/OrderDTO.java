package co.xquick.modules.shop.dto;

import co.xquick.booster.pojo.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "订单")
public class OrderDTO extends BaseDTO {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "状态")
	private Integer status;

	@ApiModelProperty(value = "订单号")
	private String no;

	@ApiModelProperty(value = "创建者")
	private Long createId;

	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	@ApiModelProperty(value = "更新者")
	private Long updateId;

	@ApiModelProperty(value = "更新时间")
	private Date updateTime;

	@ApiModelProperty(value = "删除标记")
	private Integer deleted;

	@ApiModelProperty(value = "用户表的用户ID")
	private Integer userId;

	@ApiModelProperty(value = "用户订单留言")
	private String message;

	@ApiModelProperty(value = "商品ids,用逗号隔开")
	private String productIds;

	@ApiModelProperty(value = "")
	private String productCategory;

	@ApiModelProperty(value = "")
	private String productSkus;

	@ApiModelProperty(value = "商品名称")
	private String productNames;

	@ApiModelProperty(value = "下单数量，逗号隔开")
	private String productAmount;

	@ApiModelProperty(value = "产品备注，[=====]隔开")
	private String productRemarks;

	@ApiModelProperty(value = "产品封面")
	private String productCovers;

	@ApiModelProperty(value = "单价")
	private String productPrices;

	@ApiModelProperty(value = "商品总费用")
	private BigDecimal goodsPrice;

	@ApiModelProperty(value = "配送费用")
	private BigDecimal freightPrice;

	@ApiModelProperty(value = "优惠券减免")
	private BigDecimal couponPrice;

	@ApiModelProperty(value = "用户积分减免")
	private BigDecimal integralPrice;

	@ApiModelProperty(value = "团购优惠价减免")
	private BigDecimal grouponPrice;

	@ApiModelProperty(value = "订单费用， = goods_price + freight_price - coupon_price")
	private BigDecimal orderPrice;

	@ApiModelProperty(value = "实付费用， = order_price - integral_price")
	private BigDecimal actualPrice;

	@ApiModelProperty(value = "支付ID")
	private String prepayId;

	@ApiModelProperty(value = "微信付款时间")
	private Date payTime;

	@ApiModelProperty(value = "快递单号")
	private String expressno;

	@ApiModelProperty(value = "快递寄出日期")
	private Date expressdelivedate;

	@ApiModelProperty(value = "快递名称")
	private String expressname;

	@ApiModelProperty(value = "订单内部备注")
	private String remarkmgr;

	@ApiModelProperty(value = "确认出库时间")
	private String checkdate;

	@ApiModelProperty(value = "是否确认出库")
	private Integer ischeckout;

	@ApiModelProperty(value = "优惠券ID")
	private String preferentialid;

	@ApiModelProperty(value = "收货地址id")
	private Integer receiverId;

	@ApiModelProperty(value = "收件人")
	private String receiverConsignee;

	@ApiModelProperty(value = "收件人手机号")
	private String receiverMobile;

	@ApiModelProperty(value = "收件人地址")
	private String receiverAddress;

	@ApiModelProperty(value = "订单关闭时间")
	private Date endTime;
}
