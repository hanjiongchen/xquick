package co.xquick.modules.msg.dto;

import co.xquick.booster.pojo.BaseDTO;
import co.xquick.booster.validator.group.DefaultGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * 短信模板
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "短信模板")
public class SmsTplDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称", required = true)
    @NotBlank(message = "名称不能为空", groups = DefaultGroup.class)
    private String name;

    @ApiModelProperty(value = "发送平台", required = true)
    @NotBlank(message = "发送平台不能为空", groups = DefaultGroup.class)
    private String platform;

    @ApiModelProperty(value = "编码", required = true)
    @NotBlank(message = "编码不能为空", groups = DefaultGroup.class)
    private String code;

    @ApiModelProperty(value = "发送平台配置")
    private String config;

    @ApiModelProperty(value = "短信内容")
    private String content;

}
