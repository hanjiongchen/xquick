package co.xquick.modules.msg.sms;

import co.xquick.modules.msg.dto.SmsTplDTO;

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
    public abstract void sendSms(SmsTplDTO smsTpl, String mobile, String params);

}
