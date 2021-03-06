package com.nb6868.xquick.modules.msg.sms;

import com.nb6868.xquick.booster.pojo.Const;
import com.nb6868.xquick.booster.exception.ErrorCode;
import com.nb6868.xquick.booster.exception.XquickException;
import com.nb6868.xquick.booster.util.JacksonUtils;
import com.nb6868.xquick.booster.util.SpringContextUtils;
import com.nb6868.xquick.modules.msg.entity.SmsLogEntity;
import com.nb6868.xquick.modules.msg.entity.SmsTplEntity;
import com.nb6868.xquick.modules.msg.service.SmsLogService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Map;

/**
 * 聚合短信服务
 * see {https://www.juhe.cn/docs/api/id/54}
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public class JuheSmsService extends AbstractSmsService {

    public JuheSmsService() {
    }

    private static final String JUHE_SMS_SEND_URL = "http://v.juhe.cn/sms/send?key={0}&mobile={1}&tpl_id={2}&tpl_value={3}";

    @Override
    public void sendSms(SmsTplEntity smsTpl, String mobile, String params) {
        SmsProp smsConfig = JacksonUtils.jsonToPojo(smsTpl.getConfig(), SmsProp.class);
        String url;
        StringBuilder paramJuhe = new StringBuilder();
        String content = smsTpl.getContent();
        Map<String, Object> paramJson = JacksonUtils.jsonToMap(params);
        for (String key : paramJson.keySet()) {
            // 遍历json,拼装参数
            if (StringUtils.isNotBlank(paramJuhe)) {
                paramJuhe.append("&");
            }
            paramJuhe.append("#").append(key).append("#=").append(paramJson.get(key));
            // 拼装短信内容
            content = content.replace("#" + key + "#", paramJson.get(key).toString());
        }
        try {
            url = MessageFormat.format(JUHE_SMS_SEND_URL, smsConfig.getAppKey(), mobile, smsConfig.getTplId(), URLEncoder.encode(paramJuhe.toString(), "UTF-8"));
        } catch (UnsupportedEncodingException uee) {
            throw new XquickException(ErrorCode.SEND_SMS_ERROR, uee, "组装接口地址失败");
        }
        Request request = new Request.Builder().url(url).build();

        Response response;
        try {
            response = new OkHttpClient().newCall(request).execute();
        } catch (IOException ioe) {
            throw new XquickException(ErrorCode.SEND_SMS_ERROR, ioe, "调用接口失败");
        }
        // 接口结果
        Const.ResultEnum status = response.isSuccessful() ? Const.ResultEnum.SUCCESS : Const.ResultEnum.FAIL;
        String result = "";
        if (Const.ResultEnum.SUCCESS == status) {
            try {
                assert response.body() != null;
                result = response.body().string();
            } catch (IOException ioe) {
                throw new XquickException(ErrorCode.SEND_SMS_ERROR, "接口返回数据异常");
            }
            Map<String, Object> json = JacksonUtils.jsonToMap(result);
            status = (int) json.get("error_code") == 0 ? Const.ResultEnum.SUCCESS : Const.ResultEnum.FAIL;
            if (status == Const.ResultEnum.FAIL) {
                throw new XquickException(ErrorCode.SEND_SMS_ERROR, json.get("reason").toString());
            }
        }

        //保存短信记录
        SmsLogService smsLogService = SpringContextUtils.getBean(SmsLogService.class);
        SmsLogEntity smsLog = new SmsLogEntity();
        smsLog.setMobile(mobile);
        smsLog.setStatus(status.value());
        smsLog.setResult(result);
        smsLog.setContent(content);
        smsLog.setTplId(smsTpl.getId());
        smsLog.setTplCode(smsTpl.getCode());
        smsLog.setParams(params);
        smsLog.setConsumed(0);
        smsLogService.save(smsLog);
        if (status == Const.ResultEnum.FAIL) {
            throw new XquickException(ErrorCode.SEND_SMS_ERROR);
        }
    }
}
