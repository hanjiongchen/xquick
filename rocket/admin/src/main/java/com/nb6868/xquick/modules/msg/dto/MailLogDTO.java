package com.nb6868.xquick.modules.msg.dto;

import com.nb6868.xquick.booster.pojo.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 邮件发送记录
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "邮件发送记录")
public class MailLogDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "模板编码")
    private String tplCode;

    @ApiModelProperty(value = "模板ID")
    private Long tplId;

    @ApiModelProperty(value = "发送者")
    private String mailFrom;

    @ApiModelProperty(value = "收件人")
    private String mailTo;

    @ApiModelProperty(value = "抄送者")
    private String mailCc;

    @ApiModelProperty(value = "邮件主题")
    private String subject;

    @ApiModelProperty(value = "邮件正文")
    private String content;

    @ApiModelProperty(value = "发送状态  0：失败  1：成功")
    private Integer status;

    @ApiModelProperty(value = "消费状态 0 未消费 1 已消费")
    private Integer consumed;

}
