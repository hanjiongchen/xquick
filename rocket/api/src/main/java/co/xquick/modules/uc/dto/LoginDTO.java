package co.xquick.modules.uc.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 登录表单
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "登录表单")
public class LoginDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "登录类型")
    private Integer type;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "短信验证码")
    private String code;

    @ApiModelProperty(value = "验证码")
    private String captcha;

    @ApiModelProperty(value = "唯一标识")
    private String uuid;

}
