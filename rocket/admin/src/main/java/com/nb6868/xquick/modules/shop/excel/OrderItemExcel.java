package com.nb6868.xquick.modules.shop.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
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
@EqualsAndHashCode(callSuper = false)
public class OrderItemExcel {
    @Excel(name = "id")
    private Long id;
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
    @Excel(name = "订单id")
    private Long orderId;
    @Excel(name = "spu id")
    private Long spuId;
    @Excel(name = "sku id")
    private Long skuId;
    @Excel(name = "数量")
    private BigDecimal qty;
    @Excel(name = "单价")
    private BigDecimal price;
    @Excel(name = "实付费用")
    private BigDecimal payPrice;
    @Excel(name = "spu名称")
    private String spuName;
    @Excel(name = "sku名称")
    private String skuName;
    @Excel(name = "spu封面")
    private String spuCover;
    @Excel(name = "sku封面")
    private String skuCover;

}