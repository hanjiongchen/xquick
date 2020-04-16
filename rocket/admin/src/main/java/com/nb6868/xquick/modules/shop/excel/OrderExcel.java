package com.nb6868.xquick.modules.shop.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
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
public class OrderExcel {
    @Excel(name = "id")
    private Long id;
    @Excel(name = "状态")
    private Integer status;
    @Excel(name = "订单号")
    private String no;
    @Excel(name = "创建者")
    private Long createId;
    @Excel(name = "创建时间")
    private Date createTime;
    @Excel(name = "更新者")
    private Long updateId;
    @Excel(name = "更新时间")
    private Date updateTime;
    @Excel(name = "删除标记")
    private Integer deleted;
    @Excel(name = "用户表的用户ID")
    private Integer userId;
    @Excel(name = "用户订单留言")
    private String message;
    @Excel(name = "商品ids,用逗号隔开")
    private String productIds;
    @Excel(name = "")
    private String productCategory;
    @Excel(name = "")
    private String productSkus;
    @Excel(name = "商品名称")
    private String productNames;
    @Excel(name = "下单数量，逗号隔开")
    private String productAmount;
    @Excel(name = "产品备注，[=====]隔开")
    private String productRemarks;
    @Excel(name = "产品封面")
    private String productCovers;
    @Excel(name = "单价")
    private String productPrices;
    @Excel(name = "商品总费用")
    private BigDecimal goodsPrice;
    @Excel(name = "配送费用")
    private BigDecimal freightPrice;
    @Excel(name = "优惠券减免")
    private BigDecimal couponPrice;
    @Excel(name = "用户积分减免")
    private BigDecimal integralPrice;
    @Excel(name = "团购优惠价减免")
    private BigDecimal grouponPrice;
    @Excel(name = "订单费用， = goods_price + freight_price - coupon_price")
    private BigDecimal orderPrice;
    @Excel(name = "实付费用， = order_price - integral_price")
    private BigDecimal actualPrice;
    @Excel(name = "支付ID")
    private String prepayId;
    @Excel(name = "微信付款时间")
    private Date payTime;
    @Excel(name = "快递单号")
    private String expressno;
    @Excel(name = "快递寄出日期")
    private Date expressdelivedate;
    @Excel(name = "快递名称")
    private String expressname;
    @Excel(name = "订单内部备注")
    private String remarkmgr;
    @Excel(name = "确认出库时间")
    private String checkdate;
    @Excel(name = "是否确认出库")
    private Integer ischeckout;
    @Excel(name = "优惠券ID")
    private String preferentialid;
    @Excel(name = "收货地址id")
    private Integer receiverId;
    @Excel(name = "收件人")
    private String receiverConsignee;
    @Excel(name = "收件人手机号")
    private String receiverMobile;
    @Excel(name = "收件人地址")
    private String receiverAddress;
    @Excel(name = "订单关闭时间")
    private Date endTime;
}