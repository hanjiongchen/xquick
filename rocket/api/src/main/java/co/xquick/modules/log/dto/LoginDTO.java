package co.xquick.modules.log.dto;

import co.xquick.booster.pojo.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 登录日志
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "登录日志")
public class LoginDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "登录类型")
    private Integer type;

    @ApiModelProperty(value = "用户操作  0：用户登录   1：用户退出")
    private Integer operation;

    @ApiModelProperty(value = "状态  0：失败    1：成功    2：账号已锁定")
    private Integer status;

    @ApiModelProperty(value = "用户代理")
    private String userAgent;

    @ApiModelProperty(value = "操作IP")
    private String ip;

    @ApiModelProperty(value = "用户名")
    private String createName;

}
