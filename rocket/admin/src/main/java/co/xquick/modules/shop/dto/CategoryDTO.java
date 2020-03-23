package co.xquick.modules.shop.dto;

import co.xquick.booster.util.TreeNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 商品类别
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "商品类别")
public class CategoryDTO extends TreeNode<CategoryDTO> {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "店铺id")
	private Long storeId;

	@ApiModelProperty(value = "店铺编码")
	private String storeCode;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "logo")
	private String logo;

	@ApiModelProperty(value = "排序")
	private Integer sort;

	@ApiModelProperty(value = "介绍")
	private String content;

	@ApiModelProperty(value = "上级菜单名称")
	private String parentName;

	@ApiModelProperty(value = "上级菜单列表")
	private List<CategoryDTO> parentMenuList;
}
