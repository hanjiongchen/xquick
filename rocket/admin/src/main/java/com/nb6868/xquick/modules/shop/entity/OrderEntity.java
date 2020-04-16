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
     * 状态 0：待付款  1：待发货  2:待收货  3：待评价  -1：退款   -2：售后
     */
	private Integer status;
    /**
     * 订单号
     */
	private String no;
    /**
     * 用户id
     */
	private Long userId;
	/**
	 * 下单时间
	 */
	private Date placeTime;
    /**
     * 用户备注
     */
	private String userRemark;
    /**
     * 商品费用
     */
	private BigDecimal spuPrice;
    /**
     * 订单费用
     */
	private BigDecimal price;
    /**
     * 实付费用
     */
	private BigDecimal payPrice;
    /**
     * 支付ID
     */
	private String prepayId;
    /**
     * 支付类型 0 无须支付 1 现金交易 2 银行转账 3 支付宝支付 4 微信支付
     */
	private Integer payType;
    /**
     * 付款时间
     */
	private Date payTime;
    /**
     * 物流单号
     */
	private String expressNo;
    /**
     * 物流类型
     */
	private String expressType;
    /**
     * 物流费用
     */
	private BigDecimal expressPrice;
    /**
     * 收货地址id
     */
	private Long receiverId;
    /**
     * 收件人
     */
	private String receiverConsignee;
    /**
     * 收件人电话
     */
	private String receiverMobile;
    /**
     * 收件详细地址
     */
	private String receiverAddress;
    /**
     * 收件地址区域编码
     */
	private String receiverRegionCode;
    /**
     * 收件地址区域
     */
	private String receiverRegionName;
}
