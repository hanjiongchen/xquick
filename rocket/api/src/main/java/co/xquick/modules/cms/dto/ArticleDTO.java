package co.xquick.modules.cms.dto;

import co.xquick.booster.pojo.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "文章")
public class ArticleDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "编码")
	private String code;

	@ApiModelProperty(value = "排序")
	private Integer sort;

	@ApiModelProperty(value = "状态0 未发布 1 已发布")
	private Integer status;

	@ApiModelProperty(value = "类型")
	private Integer type;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "作者")
	private String author;

	@ApiModelProperty(value = "图片")
	private String imgs;

	@ApiModelProperty(value = "文章标题")
	private String name;

	@ApiModelProperty(value = "平均评分")
	private Float score;

	@ApiModelProperty(value = "文章内容")
	private String content;

	@ApiModelProperty(value = "文章来源")
	private String source;

	@ApiModelProperty(value = "文章来源链接")
	private String sourceLink;

	@ApiModelProperty(value = "纬度")
	private Double lat;

	@ApiModelProperty(value = "经度")
	private Double lng;

	@ApiModelProperty(value = "详细地址")
	private String address;

	@ApiModelProperty(value = "所属区域编码")
	private String regionCd;

	@ApiModelProperty(value = "所属区域")
	private String regionNm;

	@ApiModelProperty(value = "发布时间")
	private Date pubDate;

	@ApiModelProperty(value = "有效期开始")
	private Date validFromDate;

	@ApiModelProperty(value = "有效期结束")
	private Date validToDate;

	@ApiModelProperty(value = "文章类别id")
	private Long articleCategoryId;

	@ApiModelProperty(value = "置顶")
	private Integer top;

	@ApiModelProperty(value = "点击次数")
	private Integer readNum;

	@ApiModelProperty(value = "文章类目")
	private String articleCategoryName;

}
