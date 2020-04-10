package co.xquick.modules.msg.push;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import co.xquick.booster.pojo.Const;
import co.xquick.booster.exception.ErrorCode;
import co.xquick.booster.exception.XquickException;
import co.xquick.booster.util.JacksonUtils;
import co.xquick.booster.util.SpringContextUtils;
import co.xquick.modules.msg.MsgConst.PushTypeEnum;
import co.xquick.modules.msg.entity.PushLogEntity;
import co.xquick.modules.msg.service.PushLogService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * JPushService 极光推送服务
 * see {https://docs.jiguang.cn/jpush/server/sdk/java_sdk/}
 * see {https://github.com/jpush/jpush-api-java-client}
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public class JPushService extends AbstractPushService {

    private static Logger logger = LoggerFactory.getLogger(JPushService.class);

    @SuppressWarnings("unchecked")
    @Override
    public void send(PushConfig config, int pushType, String alias, String tags, String title, String content, String extras, Boolean apnsProd) {
        JPushClient jpushClient = new JPushClient(config.getMasterSecret(), config.getAppKey(), null, ClientConfig.getInstance());
        Map extraMap = null;
        if (StringUtils.isNoneEmpty(extras)) {
            extraMap = JacksonUtils.jsonToMap(extras);
        }
        PushPayload payload = buildNotificationPushPayloadByAliases(pushType, StringUtils.split(alias, ","),  StringUtils.split(tags, ","), title, content, extraMap, apnsProd);

        // 保存记录
        PushLogService logService = SpringContextUtils.getBean(PushLogService.class);
        PushLogEntity log = new PushLogEntity();
        // 最后发送结果
        Const.ResultEnum status = Const.ResultEnum.FAIL;
        try {
            PushResult result = jpushClient.sendPush(payload);
            log.setResult(result.toString());
            status = result.statusCode == 0 ? Const.ResultEnum.SUCCESS : Const.ResultEnum.FAIL;
        } catch (APIConnectionException e) {
            logger.error(e.getMessage());
            log.setResult(e.toString());
        } catch (APIRequestException e) {
            // Should review the error, and fix the request
            logger.error("Should review the error, and fix the request", e);
            logger.info("HTTP Status: " + e.getStatus());
            logger.info("Error Code: " + e.getErrorCode());
            logger.info("Error Message: " + e.getErrorMessage());
            log.setResult(e.toString());
        }

        log.setType(pushType);
        log.setAlias(alias);
        log.setTags(tags);
        log.setParams(extras);
        log.setStatus(status.value());
        log.setTitle(title);
        log.setContent(content);
        logService.save(log);
        if (status == Const.ResultEnum.FAIL) {
            throw new XquickException(ErrorCode.SEND_PUSH_ERROR);
        }
    }

    /**
     * 构建推送内容
     */
    private static PushPayload buildNotificationPushPayloadByAliases(int pushType, String[] aliases, String[] tags, String title, String alert, Map<String, String> extras, Boolean apnsProd) {
        Audience.Builder audienceBuilder = Audience.newBuilder();
        audienceBuilder.setAll(pushType == PushTypeEnum.ALL.value());
        if (pushType == PushTypeEnum.ALIAS.value()) {
            if (ObjectUtils.isNotEmpty(aliases)) {
                audienceBuilder.addAudienceTarget(AudienceTarget.alias(aliases));
            } else {
                throw new XquickException("aliases不能为空");
            }
        } else if (pushType == PushTypeEnum.TAGS.value()) {
            if (ObjectUtils.isNotEmpty(tags)) {
                audienceBuilder.addAudienceTarget(AudienceTarget.tag(tags));
            } else {
                throw new XquickException("tags不能为空");
            }
        } if (pushType == PushTypeEnum.ALIAS_AND_TAGS.value()) {
            if (ObjectUtils.isNotEmpty(aliases)) {
                audienceBuilder.addAudienceTarget(AudienceTarget.alias(aliases));
            }
            if (ObjectUtils.isNotEmpty(tags)) {
                audienceBuilder.addAudienceTarget(AudienceTarget.tag(tags));
            }
        }
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(audienceBuilder.build())
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(alert)
                                .addExtras(extras)
                                .build())
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(alert)
                                .setTitle(title)
                                .addExtras(extras)
                                .build())
                        .build())
                // 指定Apns生产环境
                .setOptions(Options.newBuilder()
                        .setApnsProduction(apnsProd)
                        .build())
                .build();
    }

}
