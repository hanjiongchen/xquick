package co.xquick.modules.shop.dto;

import co.xquick.booster.pojo.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 订单记录
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "订单记录")
public class OrderLogDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "订单id")
	private Long orderId;

	@ApiModelProperty(value = "类型")
	private Integer type;

	@ApiModelProperty(value = "内容")
	private String content;

}
