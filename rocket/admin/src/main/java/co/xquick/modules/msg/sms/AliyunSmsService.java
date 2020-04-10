package co.xquick.modules.msg.sms;

import co.xquick.booster.pojo.Const;
import co.xquick.booster.exception.ErrorCode;
import co.xquick.booster.exception.XquickException;
import co.xquick.booster.util.JacksonUtils;
import co.xquick.booster.util.SpringContextUtils;
import co.xquick.common.util.TemplateUtils;
import co.xquick.modules.msg.entity.SmsLogEntity;
import co.xquick.modules.msg.entity.SmsTplEntity;
import co.xquick.modules.msg.service.SmsLogService;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import java.util.Map;

/**
 * 阿里云短信服务
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public class AliyunSmsService extends AbstractSmsService {

    private IAcsClient client;


    public AliyunSmsService() {

    }

    private void init(SmsProp smsConfig) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", smsConfig.getAppKey(), smsConfig.getAppSecret());
        client = new DefaultAcsClient(profile);
    }

    @Override
    public void sendSms(SmsTplEntity smsTpl, String mobile, String params) {
        SmsProp smsConfig = JacksonUtils.jsonToPojo(smsTpl.getConfig(), SmsProp.class);
        init(smsConfig);

        //组装请求对象
        CommonRequest request = new CommonRequest();
        //request.setSysProtocol(ProtocolType.HTTPS);
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");

        // 短信模板-可在短信控制台中找到
        request.putQueryParameter("TemplateCode", smsConfig.getTplId());
        request.putQueryParameter("RegionId", "cn-hangzhou");
        // 短信签名-可在短信控制台中找到
        request.putQueryParameter("SignName", smsConfig.getSign());
        // 接受手机号
        request.putQueryParameter("PhoneNumbers", mobile);
        // 参数
        request.putQueryParameter("TemplateParam", params);

        // 最后发送结果
        Const.ResultEnum status = Const.ResultEnum.FAIL;
        String result = "";
        CommonResponse response;
        try {
            response = client.getCommonResponse(request);
            if (response.getHttpStatus() == 200) {
                result = response.getData();
                Map<String, Object> json = JacksonUtils.jsonToMap(result);
                status = "OK".equalsIgnoreCase(json.get("Code").toString()) ? Const.ResultEnum.SUCCESS : Const.ResultEnum.FAIL;
            }
        } catch (ClientException ce) {
            ce.printStackTrace();
            throw new XquickException(ErrorCode.SEND_SMS_ERROR);
        }

        // 封装短信实际内容
        String content = smsTpl.getContent();
        content = TemplateUtils.getTemplateContent("smsContent", content, JacksonUtils.jsonToMap(params));

        // 保存短信记录
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
