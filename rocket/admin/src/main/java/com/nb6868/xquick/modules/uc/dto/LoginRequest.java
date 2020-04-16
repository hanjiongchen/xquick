package com.nb6868.xquick.modules.uc.dto;

import com.nb6868.xquick.booster.validator.group.DefaultGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 登录请求
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "登录请求")
public class LoginRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "登录类型", required = true)
    @NotNull(message = "登录类型不能为空", groups = DefaultGroup.class)
    private Integer type;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "手机号区域")
    private String mobileArea = "86";

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "短信验证码")
    private String smsCode;

    @ApiModelProperty(value = "验证码")
    private String captcha;

    @ApiModelProperty(value = "唯一标识")
    private String uuid;

    /**
     * jwt
     */
    @ApiModelProperty(value = "苹果登录token")
    private String appleIdentityToken;

}
