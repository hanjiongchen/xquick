package com.nb6868.xquick.modules.msg.sms;

import com.nb6868.xquick.modules.msg.entity.SmsTplEntity;

/**
 * 短信
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public abstract class AbstractSmsService {

    /**
     * 发送短信
     *
     * @param smsTpl 短信模板
     * @param mobile 手机号
     * @param params 短信参数
     */
    public abstract void sendSms(SmsTplEntity smsTpl, String mobile, String params);

}
