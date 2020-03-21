package co.xquick.modules.shop.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 优惠券
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CouponExcel {
    @Excel(name = "id")
    private Long id;
    @Excel(name = "商铺id")
    private Long storeId;
    @Excel(name = "名称")
    private String name;
    @Excel(name = "描述")
    private String content;
    @Excel(name = "类型")
    private Integer type;
    @Excel(name = "有效期开始")
    private Date validStartTime;
    @Excel(name = "有效期结束")
    private Date validEndTime;
    @Excel(name = "状态 0 未激活 1 已激活")
    private Integer status;
    @Excel(name = "是否可以积分兑换")
    private Integer pointExchangeEnable;
    @Excel(name = "兑换积分")
    private Integer pointExchange;
    @Excel(name = "当前数量")
    private Integer stock;
    @Excel(name = "最大商品价格")
    private BigDecimal maxPrice;
    @Excel(name = "最大sku数量")
    private Integer maxQty;
    @Excel(name = "最小商品价格")
    private BigDecimal minPrice;
    @Excel(name = "最小sku数量")
    private Integer minQty;
    @Excel(name = "价格计算表达式")
    private String priceExpress;
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