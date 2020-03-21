package co.xquick.modules.shop.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户等级
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserrankExcel {
    @Excel(name = "id")
    private Long id;
    @Excel(name = "名称")
    private String name;
    @Excel(name = "消费金额")
    private BigDecimal amount;
    @Excel(name = "默认项")
    private Integer defaultItem;
    @Excel(name = "是否特殊")
    private Integer special;
    @Excel(name = "优惠比例0-1")
    private Double scale;
    @Excel(name = "状态  0：停用   1：正常")
    private Integer status;
    @Excel(name = "创建者id")
    private Long createId;
    @Excel(name = "创建时间")
    private Date createTime;
    @Excel(name = "更新者id")
    private Long updateId;
    @Excel(name = "更新时间")
    private Date updateTime;
    @Excel(name = "逻辑删除")
    private Integer deleted;

}