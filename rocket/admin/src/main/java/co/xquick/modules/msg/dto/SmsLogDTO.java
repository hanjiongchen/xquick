package co.xquick.modules.msg.dto;

import co.xquick.booster.pojo.BaseDTO;
import co.xquick.booster.validator.group.AddGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * 短信发送记录
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "短信发送记录")
public class SmsLogDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "模板ID")
	private Long tplId;

	@ApiModelProperty(value = "模板编码", required = true)
	@NotBlank(message="模板编码不能为空", groups = AddGroup.class)
	private String tplCode;

	@ApiModelProperty(value = "短信内容")
	private String content;

	@ApiModelProperty(value = "参数")
	private String params;

	@ApiModelProperty(value = "手机号")
	@NotBlank(message="手机号不能为空", groups = AddGroup.class)
	private String mobile;

	@ApiModelProperty(value = "发送结果")
	private String result;

	@ApiModelProperty(value = "发送状态  0：失败  1：成功")
	private Integer status;

	@ApiModelProperty(value = "消费状态 0 未消费 1 已消费")
	private Integer consumed;

}
