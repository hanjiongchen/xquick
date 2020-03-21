package co.xquick.modules.shop.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

}