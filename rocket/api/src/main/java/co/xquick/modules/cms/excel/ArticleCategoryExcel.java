package co.xquick.modules.cms.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 文章类目
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ArticleCategoryExcel {
    @Excel(name = "id")
    private Long id;
    @Excel(name = "编码")
    private String code;
    @Excel(name = "名称")
    private String name;
    @Excel(name = "排序")
    private Integer sort;
    @Excel(name = "状态")
    private Integer status;
    @Excel(name = "类型")
    private Integer type;
    @Excel(name = "备注")
    private String remark;
    @Excel(name = "创建者")
    private Long creator;
    @Excel(name = "创建时间")
    private Date createDate;
    @Excel(name = "更新者")
    private Long updater;
    @Excel(name = "更新时间")
    private Date updateDate;
    @Excel(name = "删除标记")
    private Integer delFlag;

}