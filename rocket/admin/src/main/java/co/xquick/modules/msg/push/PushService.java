package co.xquick.modules.msg.push;

import co.xquick.booster.constant.Constant;
import co.xquick.booster.exception.XquickException;
import co.xquick.modules.sys.service.ParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 推送服务
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class PushService {

    @Autowired
    ParamService paramsService;

    public void send(int pushType, String alias, String tags, String title, String content, String extras, Boolean apnsProd) {
        PushConfig config = paramsService.getContentObject(Constant.PUSH_CONFIG_KEY, PushConfig.class);
        if (config == null) {
            throw new XquickException("未找到对应的推送配置");
        }
        send(config, pushType, alias, tags, title, content, extras, apnsProd);
    }

    public void send(PushConfig config, int pushType, String alias, String tags, String title, String content, String extras, Boolean apnsProd) {
        // 获取推送服务
        AbstractPushService service = PushFactory.build(config);
        // 发送推送
        service.send(config, pushType, alias, tags, title, content, extras, apnsProd);
    }
}
