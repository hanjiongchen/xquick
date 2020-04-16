package com.nb6868.xquick.modules.shop.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.nb6868.xquick.booster.pojo.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单明细
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("shop_order_item")
public class OrderItemEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

    /**
     * 订单id
     */
	private Long orderId;
    /**
     * spu id
     */
	private Long spuId;
    /**
     * sku id
     */
	private Long skuId;
    /**
     * 数量
     */
	private BigDecimal qty;
    /**
     * 单价
     */
	private BigDecimal price;
    /**
     * 实付费用
     */
	private BigDecimal payPrice;
    /**
     * spu名称
     */
	private String spuName;
    /**
     * sku名称
     */
	private String skuName;
    /**
     * spu封面
     */
	private String spuCover;
    /**
     * sku封面
     */
	private String skuCover;
}
