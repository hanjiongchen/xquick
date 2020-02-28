package co.xquick.modules.uc.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 登录配置信息
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "登录配置表单")
public class LoginConfigDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "登录类型")
    private Integer type;

    @JsonIgnore
    @ApiModelProperty(value = "首次登录强制修改密码")
    private Boolean forceUpdatePassword;

    @JsonIgnore
    @ApiModelProperty(value = "支持多客户端登录")
    private Boolean multi;

    @JsonIgnore
    @ApiModelProperty(value = "Token有效时间")
    private Long expire;

    @JsonIgnore
    @ApiModelProperty(value = "短信验证码有效毫秒长")
    private Long smsCodeValidTime;

    @JsonIgnore
    @ApiModelProperty(value = "自动延期")
    private Boolean renewal;

    @ApiModelProperty(value = "验证码支持")
    private Boolean captcha;

    @JsonIgnore
    @ApiModelProperty(value = "魔法验证码")
    private String magicCaptcha;

    @JsonIgnore
    @ApiModelProperty(value = "自动创建用户")
    private Boolean autoCreate;

    @JsonIgnore
    @ApiModelProperty(value = "最多登录次数")
    private Integer tryTimesMax;

    @JsonIgnore
    @ApiModelProperty(value = "超过最大登录次数后锁定时间")
    private Long loginErrorLockTime;

    @JsonIgnore
    @ApiModelProperty(value = "自动创建用户的角色ids")
    private String autoCreateUserRoleIds;

}
