package co.xquick.modules.shop.dto;

import co.xquick.booster.pojo.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 商品类别
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "商品类别")
public class CategoryDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "父id")
	private Long pid;

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

}
