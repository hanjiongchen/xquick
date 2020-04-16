package com.nb6868.xquick.modules.shop.dto;

import com.nb6868.xquick.booster.pojo.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 品牌
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "品牌")
public class BrandDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "图片")
	private String imgs;

	@ApiModelProperty(value = "排序")
	private Integer sort;

	@ApiModelProperty(value = "品牌介绍")
	private String content;

	@ApiModelProperty(value = "状态0 未审核 1 已审核")
	private Integer status;

}
