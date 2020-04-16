package com.nb6868.xquick.modules.shop.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商城
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class StoreExcel {
    @Excel(name = "编码")
    private String code;
    @Excel(name = "名称")
    private String name;
    @Excel(name = "logo")
    private String logo;
    @Excel(name = "联系电话")
    private String tel;
    @Excel(name = "排序")
    private Integer sort;
    @Excel(name = "状态", replace = {"未审核_0", "已审核_1"})
    private Integer status;
    @Excel(name = "介绍")
    private String content;
}