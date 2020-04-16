package com.nb6868.xquick.modules.msg.entity;

import com.nb6868.xquick.booster.pojo.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 邮件发送记录
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("msg_mail_log")
public class MailLogEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 模板ID
     */
    private Long tplId;
    /**
     * 模板编码
     */
    private String tplCode;
    /**
     * 发送者
     */
    private String mailFrom;
    /**
     * 收件人
     */
    private String mailTo;
    /**
     * 抄送者
     */
    private String mailCc;
    /**
     * 邮件主题
     */
    private String subject;
    /**
     * 邮件正文
     */
    private String content;
    /**
     * 发送状态  0：失败  1：成功
     */
    private Integer status;
    /**
     * 消费状态 0 未消费 1 已消费
     */
    private Integer consumed;

}
