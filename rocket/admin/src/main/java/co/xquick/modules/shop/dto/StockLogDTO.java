package co.xquick.modules.shop.dto;

import co.xquick.booster.pojo.BaseDTO;
import co.xquick.booster.validator.group.DefaultGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * 出入库记录
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "出入库记录")
public class StockLogDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "spu id")
	private Long spuId;

	@ApiModelProperty(value = "sku id")
	@NotNull(message = "商品规格id不能为空", groups = DefaultGroup.class)
	private Long skuId;

	@ApiModelProperty(value = "类型", required = true)
	@Range(min = 0, max = 1, message = "类型取值0-1", groups = DefaultGroup.class)
	private Integer type;

	@ApiModelProperty(value = "入库数量")
	@Range(min = 0, max = 99999, message = "入库数量取值0-99999", groups = DefaultGroup.class)
	private Integer inQty;

	@ApiModelProperty(value = "出库数量")
	@Range(min = 0, max = 99999, message = "出库数量取值0-99999", groups = DefaultGroup.class)
	private Integer outQty;

	@ApiModelProperty(value = "出入库后库存")
	private Integer stock;

}
