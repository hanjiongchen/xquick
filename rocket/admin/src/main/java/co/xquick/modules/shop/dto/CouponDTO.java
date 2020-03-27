package co.xquick.modules.shop.dto;

import co.xquick.booster.pojo.BaseDTO;
import co.xquick.booster.validator.group.DefaultGroup;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 优惠券
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "优惠券")
public class CouponDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "商铺id")
	private Long storeId;

	@ApiModelProperty(value = "名称", required = true)
	@NotBlank(message = "名称不能为空", groups = DefaultGroup.class)
	private String name;

	@ApiModelProperty(value = "描述")
	private String content;

	@ApiModelProperty(value = "类型,1 满减券", required = true)
	@Range(min = 1, max = 1, message = "类型取值限制", groups = DefaultGroup.class)
	private Integer type;

	@ApiModelProperty(value = "有效期开始", required = true)
	private Date validStartTime;

	@ApiModelProperty(value = "有效期结束", required = true)
	private Date validEndTime;

	@ApiModelProperty(value = "发放方式, 1注册赠送 2积分兑换 3用户领取", required = true)
	@Range(min = 1, max = 3, message = "发放方式取值1-3", groups = DefaultGroup.class)
	private Integer giveType;

	@ApiModelProperty(value = "状态 0 未激活 1 已激活", required = true)
	@Range(min = 0, max = 1, message = "状态取值0-1", groups = DefaultGroup.class)
	private Integer status;

	@ApiModelProperty(value = "兑换积分", required = true)
	@Range(min = 0,  message = "兑换积分取值限制", groups = DefaultGroup.class)
	private Integer pointExchange;

	@ApiModelProperty(value = "发放总量", required = true)
	@Range(min = -1, message = "发放总量限制", groups = DefaultGroup.class)
	private Integer totalQty;

	@ApiModelProperty(value = "用户领取限制", required = true)
	@Range(min = -1, message = "发放总量限制", groups = DefaultGroup.class)
	private Integer userQtyLimit;

	@ApiModelProperty(value = "已发放量")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Integer giveQty;

	@ApiModelProperty(value = "限制商品类别")
	private Long limitSpuCategoryId;

	@ApiModelProperty(value = "限制商品类别")
	private String limitSpuCategoryName;

	@ApiModelProperty(value = "限制spu")
	private Long limitSpuId;

	@ApiModelProperty(value = "限制spu")
	private String limitSpuName;

	@ApiModelProperty(value = "限制sku")
	private Long limitSkuId;

	@ApiModelProperty(value = "限制sku")
	private String limitSkuName;

	@ApiModelProperty(value = "达标价格")
	private BigDecimal limitPrice;

	@ApiModelProperty(value = "减免价格")
	private BigDecimal reducedPrice;

	@ApiModelProperty(value = "价格计算表达式")
	private String priceExpress;

}
