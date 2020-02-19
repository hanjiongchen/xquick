package co.xquick.modules.cms.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 文章
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ArticleExcel {
    @Excel(name = "id")
    private Long id;
    @Excel(name = "编码")
    private String code;
    @Excel(name = "排序")
    private Integer sort;
    @Excel(name = "状态0 未发布 1 已发布")
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
    @Excel(name = "文章标题")
    private String name;
    @Excel(name = "平均评分")
    private Float score;
    @Excel(name = "文章内容")
    private String content;
    @Excel(name = "纬度")
    private Double lat;
    @Excel(name = "经度")
    private Double lng;
    @Excel(name = "详细地址")
    private String address;
    @Excel(name = "所属区域编码")
    private String regionCd;
    @Excel(name = "所属区域")
    private String regionNm;
    @Excel(name = "发布时间")
    private Date pubDate;
    @Excel(name = "有效期开始")
    private Date validFromDate;
    @Excel(name = "有效期结束")
    private Date validToDate;
    @Excel(name = "文章类别id")
    private Long articleCategoryId;

}
