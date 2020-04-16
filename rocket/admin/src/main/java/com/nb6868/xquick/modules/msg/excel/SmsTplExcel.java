package com.nb6868.xquick.modules.msg.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 短信模板
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SmsTplExcel {
    @Excel(name = "id")
    private Long id;
    @Excel(name = "模板名称")
    private String name;
    @Excel(name = "短信方配置")
    private String config;
    @Excel(name = "短信参数")
    private String param;
    @Excel(name = "短信内容")
    private String content;
    @Excel(name = "创建者")
    private Long creator;
    @Excel(name = "创建时间")
    private Date createDate;
    @Excel(name = "更新者")
    private Long updater;
    @Excel(name = "更新时间")
    private Date updateDate;
    @Excel(name = "软删标记")
    private Integer delFlag;

}
