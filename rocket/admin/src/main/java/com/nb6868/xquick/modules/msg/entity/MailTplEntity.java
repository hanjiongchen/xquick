package com.nb6868.xquick.modules.msg.entity;

import com.nb6868.xquick.booster.pojo.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 邮件模板
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("msg_mail_tpl")
public class MailTplEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编码
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 标题
     */
    private String title;
    /**
     * 正文
     */
    private String content;
    /**
     * 发件人Host
     */
    private String senderHost;
    /**
     * 发件人Host端口
     */
    private Integer senderHostPort;

    /**
     * 发件人帐号
     */
    private String senderUsername;

    /**
     * 发件人密码
     */
    private String senderPassword;

}
