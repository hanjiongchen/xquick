package co.xquick.modules.shop.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 供应商
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SupplierExcel {
    @Excel(name = "id")
    private Long id;
    @Excel(name = "编码")
    private String code;
    @Excel(name = "名称")
    private String name;
    @Excel(name = "备注")
    private String remark;
    @Excel(name = "图片")
    private String imgs;
    @Excel(name = "排序")
    private Integer sort;
    @Excel(name = "状态0 未审核 1 已审核")
    private Integer status;
    @Excel(name = "内容")
    private String content;
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