package com.nb6868.xquick.modules.shop.dto;

import com.nb6868.xquick.booster.pojo.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商城
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "商城")
public class StoreDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "编码")
	private String code;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "logo")
	private String logo;

	@ApiModelProperty(value = "联系电话")
	private String tel;

	@ApiModelProperty(value = "类型")
	private Integer type;

	@ApiModelProperty(value = "排序")
	private Integer sort;

	@ApiModelProperty(value = "状态0 未审核 1 已审核")
	private Integer status;

	@ApiModelProperty(value = "介绍")
	private String content;

}
