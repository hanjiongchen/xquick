package co.xquick.modules.shop.dto;

import co.xquick.booster.pojo.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 商品规格sku
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "商品规格sku")
public class SkuDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "商品id")
	private Long spuId;

	@ApiModelProperty(value = "商品图片,空则使用procut表的imgs")
	private String spuImgs;

	@ApiModelProperty(value = "市场价")
	private BigDecimal marketPrice;

	@ApiModelProperty(value = "销售价")
	private BigDecimal salePrice;

	@ApiModelProperty(value = "是否默认项")
	private Integer defaultItem;

	@ApiModelProperty(value = "当前库存")
	private Integer stock;

	@ApiModelProperty(value = "编号")
	private String sn;

	@ApiModelProperty(value = "名称")
	private String name;

}
