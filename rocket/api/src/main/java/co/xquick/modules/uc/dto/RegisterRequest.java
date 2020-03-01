package co.xquick.modules.uc.dto;

import co.xquick.booster.validator.group.DefaultGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 注册请求
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "注册请求")
public class RegisterRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "密码", required = true)
    @NotEmpty(message = "密码不能为空", groups = DefaultGroup.class)
    private String password;

    @ApiModelProperty(value = "短信验证码", required = true)
    @NotEmpty(message = "短信验证码不能为空", groups = DefaultGroup.class)
    private String smsCode;

    @ApiModelProperty(value = "手机号区域")
    private String mobileArea = "86";

    @ApiModelProperty(value = "手机号", required = true)
    @NotEmpty(message = "手机号不能为空", groups = DefaultGroup.class)
    private String mobile;

    @ApiModelProperty(value = "用户名", required = true)
    @NotEmpty(message = "用户名不能为空", groups = DefaultGroup.class)
    private String username;

}
