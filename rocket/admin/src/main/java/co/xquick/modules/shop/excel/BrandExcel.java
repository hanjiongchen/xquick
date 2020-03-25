package co.xquick.modules.shop.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 品牌
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BrandExcel {
    @Excel(name = "名称")
    private String name;
    @Excel(name = "备注")
    private String remark;
    @Excel(name = "图片")
    private String imgs;
    @Excel(name = "排序")
    private Integer sort;
    @Excel(name = "品牌介绍")
    private String content;

}