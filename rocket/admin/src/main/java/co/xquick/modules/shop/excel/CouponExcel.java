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
    @Excel(name = "商铺")
    private String storeName;
    @Excel(name = "名称")
    private String name;
    @Excel(name = "描述")
    private String content;
    @Excel(name = "类型", replace = {"满减券_1"})
    private Integer type;
    @Excel(name = "有效期开始", format = "yyyy-MM-dd HH:mm:ss")
    private Date validStartTime;
    @Excel(name = "有效期结束", format = "yyyy-MM-dd HH:mm:ss")
    private Date validEndTime;
    @Excel(name = "状态", replace = {"未审核_0", "已激活_1"})
    private Integer status;
    @Excel(name = "是否可以积分兑换", replace = {"否_0", "是_1"})
    private Integer pointExchangeEnable;
    @Excel(name = "兑换积分")
    private Integer pointExchange;
    @Excel(name = "当前数量")
    private Integer stock;

}