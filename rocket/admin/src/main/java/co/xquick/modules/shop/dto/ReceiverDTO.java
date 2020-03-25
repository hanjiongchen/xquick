package co.xquick.modules.shop.dto;

import co.xquick.booster.pojo.BaseDTO;
import co.xquick.booster.validator.group.DefaultGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 收件地址
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "收件地址")
public class ReceiverDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户id", required = true)
	@NotBlank(message = "用户id不能为空", groups = DefaultGroup.class)
	private Long userId;

	@ApiModelProperty(value = "纬度")
	private Double lat;

	@ApiModelProperty(value = "经度")
	private Double lng;

	@ApiModelProperty(value = "标签,如家、公司等")
	private String tag;

	@ApiModelProperty(value = "区域名称,如浙江省,宁波市,鄞州区")
	private String regionName;

	@ApiModelProperty(value = "区域编号,如33000,33010,33011")
	private String regionCode;

	@ApiModelProperty(value = "详细门牌号")
	private String address;

	@ApiModelProperty(value = "收件人")
	private String consignee;

	@ApiModelProperty(value = "邮编")
	private String zipCode;

	@ApiModelProperty(value = "收件人手机号")
	private String mobile;

	@ApiModelProperty(value = "默认项", required = true)
	@Range(min = 0, max = 1, message = "默认项取值0-1", groups = DefaultGroup.class)
	private Integer defaultItem;

}
