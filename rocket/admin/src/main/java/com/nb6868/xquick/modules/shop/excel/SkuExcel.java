package com.nb6868.xquick.modules.shop.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品规格sku
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SkuExcel {
    @Excel(name = "id")
    private Long id;
    @Excel(name = "商品id")
    private Long spuId;
    @Excel(name = "商品图片,空则使用procut表的imgs")
    private String spuImgs;
    @Excel(name = "市场价")
    private BigDecimal marketPrice;
    @Excel(name = "销售价")
    private BigDecimal salePrice;
    @Excel(name = "是否默认项")
    private Integer isDefault;
    @Excel(name = "当前库存")
    private Integer stock;
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

}