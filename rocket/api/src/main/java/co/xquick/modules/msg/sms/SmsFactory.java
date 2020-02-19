package co.xquick.modules.msg.sms;

import co.xquick.booster.exception.ErrorCode;
import co.xquick.booster.exception.XquickException;

/**
 * 短信Factory
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public class SmsFactory {

    public static AbstractSmsService build(String platform) {
        // 获取短信配置信息
        if ("aliyun".equalsIgnoreCase(platform)) {
            return new AliyunSmsService();
        } else if ("juhe".equalsIgnoreCase(platform)) {
            return new JuheSmsService();
        } else {
            throw new XquickException(ErrorCode.SMS_CONFIG_ERROR);
        }
    }
}
