package com.nb6868.xquick.modules.shop.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.nb6868.xquick.booster.pojo.BaseEntity;
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
@EqualsAndHashCode(callSuper=false)
@TableName("shop_order")
public class OrderEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 订单号
	 */
	private String no;
	/**
	 * 创建者
	 */
	private Long createId;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新者
	 */
	private Long updateId;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 删除标记
	 */
	private Integer deleted;
	/**
	 * 用户表的用户ID
	 */
	private Integer userId;
	/**
	 * 用户订单留言
	 */
	private String message;
	/**
	 * 商品ids,用逗号隔开
	 */
	private String productIds;
	/**
	 *
	 */
	private String productCategory;
	/**
	 *
	 */
	private String productSkus;
	/**
	 * 商品名称
	 */
	private String productNames;
	/**
	 * 下单数量，逗号隔开
	 */
	private String productAmount;
	/**
	 * 产品备注，[=====]隔开
	 */
	private String productRemarks;
	/**
	 * 产品封面
	 */
	private String productCovers;
	/**
	 * 单价
	 */
	private String productPrices;
	/**
	 * 商品总费用
	 */
	private BigDecimal goodsPrice;
	/**
	 * 配送费用
	 */
	private BigDecimal freightPrice;
	/**
	 * 优惠券减免
	 */
	private BigDecimal couponPrice;
	/**
	 * 用户积分减免
	 */
	private BigDecimal integralPrice;
	/**
	 * 团购优惠价减免
	 */
	private BigDecimal grouponPrice;
	/**
	 * 订单费用， = goods_price + freight_price - coupon_price
	 */
	private BigDecimal orderPrice;
	/**
	 * 实付费用， = order_price - integral_price
	 */
	private BigDecimal actualPrice;
	/**
	 * 支付ID
	 */
	private String prepayId;
	/**
	 * 微信付款时间
	 */
	private Date payTime;
	/**
	 * 快递单号
	 */
	private String expressno;
	/**
	 * 快递寄出日期
	 */
	private Date expressdelivedate;
	/**
	 * 快递名称
	 */
	private String expressname;
	/**
	 * 订单内部备注
	 */
	private String remarkmgr;
	/**
	 * 确认出库时间
	 */
	private String checkdate;
	/**
	 * 是否确认出库
	 */
	private Integer ischeckout;
	/**
	 * 优惠券ID
	 */
	private String preferentialid;
	/**
	 * 收货地址id
	 */
	private Integer receiverId;
	/**
	 * 收件人
	 */
	private String receiverConsignee;
	/**
	 * 收件人手机号
	 */
	private String receiverMobile;
	/**
	 * 收件人地址
	 */
	private String receiverAddress;
	/**
	 * 订单关闭时间
	 */
	private Date endTime;
}
