package co.xquick.modules.shop.dto;

import co.xquick.booster.pojo.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

import java.math.BigDecimal;

/**
 * 用户等级
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "用户等级")
public class UserrankDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "消费金额")
	private BigDecimal amount;

	@ApiModelProperty(value = "默认项")
	private Integer defaultItem;

	@ApiModelProperty(value = "是否特殊")
	private Integer special;

	@ApiModelProperty(value = "优惠比例0-1")
	private Double scale;

	@ApiModelProperty(value = "状态  0：停用   1：正常")
	private Integer status;

}
