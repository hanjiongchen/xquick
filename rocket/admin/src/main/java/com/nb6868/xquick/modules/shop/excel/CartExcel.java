package com.nb6868.xquick.modules.shop.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 购物车
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CartExcel {
    @Excel(name = "id")
    private Long id;
    @Excel(name = "会员id")
    private Long userId;
    @Excel(name = "spu id")
    private Long spuId;
    @Excel(name = "sku id")
    private Long skuId;
    @Excel(name = "商铺id")
    private Long storeId;
    @Excel(name = "数量")
    private Integer qty;
    @Excel(name = "状态0 未下单 1 已下单")
    private Integer status;
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