package co.xquick.modules.msg.dto;

import co.xquick.booster.validator.group.DefaultGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 短信发送请求
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "短信发送请求")
public class SmsSendRequest implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "手机号区域", required = true)
	@NotBlank(message="手机号区域不能为空", groups = DefaultGroup.class)
	private String mobileArea = "86";

	@ApiModelProperty(value = "手机号", required = true)
	@NotBlank(message="手机号不能为空", groups = DefaultGroup.class)
	private String mobile;

	@ApiModelProperty(value = "模板编码", required = true)
	@NotBlank(message="模板编码不能为空", groups = DefaultGroup.class)
	private String tplCode;

	@ApiModelProperty(value = "参数")
	private String param;

}
