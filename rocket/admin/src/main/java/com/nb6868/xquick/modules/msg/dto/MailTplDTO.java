package com.nb6868.xquick.modules.msg.dto;

import com.nb6868.xquick.booster.pojo.BaseDTO;
import com.nb6868.xquick.booster.validator.group.DefaultGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

/**
 * 邮件模板
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "邮件模板")
public class MailTplDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编码")
    @NotBlank(message = "编码不能为空", groups = DefaultGroup.class)
    private String code;

    @ApiModelProperty(value = "名称")
    @NotBlank(message = "名称不能为空", groups = DefaultGroup.class)
    private String name;

    @ApiModelProperty(value = "邮件标题")
    @NotBlank(message = "邮件标题不能为空", groups = DefaultGroup.class)
    private String title;

    @ApiModelProperty(value = "邮件正文")
    @NotBlank(message = "邮件正文不能为空", groups = DefaultGroup.class)
    private String content;

    @ApiModelProperty(value = "发件Host")
    @NotBlank(message = "发件Host不能为空", groups = DefaultGroup.class)
    private String senderHost;

    @ApiModelProperty(value = "发件Host端口")
    @Range(min = 0, max = 65536, message = "端口取值0-65536", groups = DefaultGroup.class)
    private Integer senderHostPort;

    @ApiModelProperty(value = "发件帐号")
    @NotBlank(message = "发件帐号不能为空", groups = DefaultGroup.class)
    private String senderUsername;

    @ApiModelProperty(value = "发件密码")
    @NotBlank(message = "发件密码不能为空", groups = DefaultGroup.class)
    private String senderPassword;

}
