package co.xquick.modules.shop.dto;

import co.xquick.booster.pojo.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

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
	private Long skuId;

	@ApiModelProperty(value = "类型 0 入库 1 出库")
	private Integer type;

	@ApiModelProperty(value = "入库数量")
	private Integer inQty;

	@ApiModelProperty(value = "出库数量")
	private Integer outQty;

	@ApiModelProperty(value = "出入库后库存")
	private Integer stock;

}
