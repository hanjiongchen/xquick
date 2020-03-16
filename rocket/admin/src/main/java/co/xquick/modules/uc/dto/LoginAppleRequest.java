package co.xquick.modules.uc.dto;

import co.xquick.booster.validator.group.DefaultGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 苹果登录请求
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "苹果登录请求")
public class LoginAppleRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "登录类型", required = true)
    @NotNull(message = "登录类型不能为空", groups = DefaultGroup.class)
    private Integer type;

    @ApiModelProperty(value = "苹果登录获取token")
    @NotNull(message = "identityToken不能为空", groups = DefaultGroup.class)
    private String identityToken;

    @ApiModelProperty(value = "苹果登录获取用户id")
    @NotNull(message = "userIdentifier不能为空", groups = DefaultGroup.class)
    private String userIdentifier;

}
