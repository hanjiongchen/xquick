package com.nb6868.xquick.modules.shop.dto;

import com.nb6868.xquick.booster.pojo.BaseDTO;
import com.nb6868.xquick.booster.validator.group.DefaultGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 商品类别
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "商品类别")
public class SpuCategoryDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "上级ID")
	@NotNull(message="请选择上级", groups = DefaultGroup.class)
	private Long pid;

	@ApiModelProperty(value = "店铺id")
	private Long storeId;

	@ApiModelProperty(value = "店铺编码")
	private String storeCode;

	@ApiModelProperty(value = "名称")
	@NotNull(message="名称不能为空", groups = DefaultGroup.class)
	private String name;

	@ApiModelProperty(value = "logo")
	private String logo;

	@ApiModelProperty(value = "排序")
	@Min(value = 0, message = "{sort.number}", groups = DefaultGroup.class)
	private Integer sort;

	@ApiModelProperty(value = "介绍")
	private String content;

	@ApiModelProperty(value = "上级菜单名称")
	private String parentName;

	@ApiModelProperty(value = "上级菜单列表")
	private List<SpuCategoryDTO> parentMenuList;
}
