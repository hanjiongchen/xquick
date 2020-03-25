package co.xquick.modules.shop.dto;

import co.xquick.booster.pojo.BaseDTO;
import co.xquick.booster.validator.group.DefaultGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
public class UserRankDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "名称", required = true)
	@NotBlank(message = "名称必填", groups = DefaultGroup.class)
	private String name;

	@ApiModelProperty(value = "消费金额")
	private BigDecimal amount;

	@ApiModelProperty(value = "排序")
	private Integer sort;

	@ApiModelProperty(value = "默认项")
	private Integer defaultItem;

	@ApiModelProperty(value = "是否特殊")
	private Integer special;

	@ApiModelProperty(value = "优惠比例", required = true)
	@Range(min = 0, max = 1, message = "优惠比例取值0-1", groups = DefaultGroup.class)
	private Double scale;

	@ApiModelProperty(value = "状态  0：停用   1：正常", required = true)
	@Range(min = 0, max = 1, message = "状态取值0-1", groups = DefaultGroup.class)
	private Integer status;

}
