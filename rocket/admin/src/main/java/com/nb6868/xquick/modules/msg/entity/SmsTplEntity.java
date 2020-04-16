package com.nb6868.xquick.modules.msg.entity;

import com.nb6868.xquick.booster.pojo.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 短信模板
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("msg_sms_tpl")
public class SmsTplEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 模板名称
     */
    private String name;
    /**
     * 模板编号
     */
    private String code;
    /**
     * 发送平台
     */
    private String platform;
    /**
     * 发送平台配置
     */
    private String config;
    /**
     * 短信内容
     */
    private String content;
}
