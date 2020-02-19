package co.xquick.modules.msg.push;

/**
 * 消息推送
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public abstract class AbstractPushService {

    /**
     * 发送
     */
    public abstract void send(PushConfig config, int pushType, String alias, String tags, String title, String content, String extras, Boolean apnsProd);

}
