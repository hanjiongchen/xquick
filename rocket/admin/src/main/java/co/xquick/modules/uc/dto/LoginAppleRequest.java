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

    /**
     * 类似
     * eyJraWQiOiJlWGF1bm1MIiwiYWxnIjoiUlMyNTYifQ.eyJpc3MiOiJodHRwczovL2FwcGxlaWQuYXBwbGUuY29tIiwiYXVkIjoiY29tLm5ieHVkYS5leGNoYW5nZTIiLCJleHAiOjE1ODQzNTQ0NTEsImlhdCI6MTU4NDM1Mzg1MSwic3ViIjoiMDAxMzQzLjNkODlkZmU5NWQ4MTQyYTRiMTZhMGEyMjZiNTM2NjU5LjA2MzMiLCJjX2hhc2giOiJzMWNBbjRkWTktbkdxMmw1cnZkRWV3IiwiZW1haWwiOiIxNTg2ODgxNDExMkAxNjMuY29tIiwiZW1haWxfdmVyaWZpZWQiOiJ0cnVlIiwiYXV0aF90aW1lIjoxNTg0MzUzODUxLCJub25jZV9zdXBwb3J0ZWQiOnRydWV9.KkkISSuPaOUyHhEUOVpLuFtKbVhDylnvI8YXxssOXOImU0UiYJIuSKBNFzSoesmOAYH2nzic5o1BX9BwkfJOulZQybgwXcbtWqQCA3YjyZ7uoZE7KjY2TelYPUNbR6zYjlH8B1GQCPq9EmeQNMpvJN2pnqfC9rTWcBDxHH-yHZ6hU0Ch0j9TAWyFp33U9DLrBscEoHc_w3nLk0gqIHAEQmkgsy2GQFumieYnttEow4PlkOkKGaA7bG9ud8i1tOStZ6JwWdq-T2ZdrnW6MvyzFBIXnVCjgr0dPNgX5gN6UmNbjsAloWkhJwDcCJ4AOH11GfUSBWrAZEjQG-Rf_gk06g
     */
    @ApiModelProperty(value = "苹果登录获取token")
    @NotNull(message = "identityToken不能为空", groups = DefaultGroup.class)
    // @Pattern(regexp = "[a-zA-Z]{5,20}", message = "identityToken格式错误", groups = DefaultGroup.class)
    private String identityToken;

}
