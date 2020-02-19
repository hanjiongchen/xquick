package co.xquick.modules.msg.push;

import co.xquick.booster.exception.ErrorCode;
import co.xquick.booster.exception.XquickException;

/**
 * PushFactory
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public class PushFactory {

    public static AbstractPushService build(PushConfig config) {
        // 获取推送配置信息
        if ("jpush".equalsIgnoreCase(config.getPlatform())) {
            return new JPushService();
        } else {
            throw new XquickException(ErrorCode.PUSH_CONFIG_ERROR);
        }
    }
}
