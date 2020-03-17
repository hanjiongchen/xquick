package co.xquick.modules.msg.service.impl;

import co.xquick.booster.exception.ErrorCode;
import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.booster.util.ParamUtils;
import co.xquick.booster.validator.AssertUtils;
import co.xquick.modules.msg.dao.SmsLogDao;
import co.xquick.modules.msg.dto.SmsLogDTO;
import co.xquick.modules.msg.dto.SmsSendRequest;
import co.xquick.modules.msg.entity.SmsLogEntity;
import co.xquick.modules.msg.entity.SmsTplEntity;
import co.xquick.modules.msg.service.SmsLogService;
import co.xquick.modules.msg.service.SmsTplService;
import co.xquick.modules.msg.sms.AbstractSmsService;
import co.xquick.modules.msg.sms.SmsFactory;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Map;

/**
 * 短信发送记录
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class SmsLogServiceImpl extends CrudServiceImpl<SmsLogDao, SmsLogEntity, SmsLogDTO> implements SmsLogService {

    @Autowired
    SmsTplService smsTemplateService;

    @Override
    public QueryWrapper<SmsLogEntity> getWrapper(String method, Map<String, Object> params) {
        return new QueryWrapper<SmsLogEntity>()
                .eq(ParamUtils.isNotEmpty(params.get("tplId")), "tpl_id", params.get("tplId"))
                .eq(ParamUtils.isNotEmpty(params.get("tplCode")), "tpl_code", params.get("tplCode"))
                .like(ParamUtils.isNotEmpty(params.get("content")), "content", params.get("content"))
                .like(ParamUtils.isNotEmpty(params.get("params")), "params", params.get("params"))
                .like(ParamUtils.isNotEmpty(params.get("result")), "result", params.get("result"))
                .eq(ParamUtils.isNotEmpty(params.get("status")), "status", params.get("status"))
                .eq(ParamUtils.isNotEmpty(params.get("mobile")), "mobile", params.get("mobile"));
    }

    /**
     * 消费短信
     */
    @Override
    public void consumeById(Serializable id) {
        update(currentModel(), new UpdateWrapper<SmsLogEntity>().set("consumed", 1).eq("id", id));
    }

    @Override
    public void send(SmsSendRequest dto) {
        // 获得template
        SmsTplEntity smsTpl = smsTemplateService.getByCode(dto.getTplCode());

        AssertUtils.isNull(smsTpl, ErrorCode.SMS_TPL_NOT_EXISTED);

        // 获取短信服务
        AbstractSmsService service = SmsFactory.build(smsTpl.getPlatform());

        // 发送短信
        service.sendSms(smsTpl, dto.getMobile(), dto.getParam());
    }

    @Override
    public SmsLogEntity findLastLog(Long tplId, String mobile) {
        return query().eq("tpl_id", tplId)
                .eq("mobile", mobile)
                .eq("status", 1)
                .eq("consumed", 0)
                .orderByDesc("create_time")
                .last("limit 1").one();
    }

    @Override
    public SmsLogEntity findLastLogByTplCode(String tplCode, String mobile) {
        return query().eq("tpl_code", tplCode)
                .eq("mobile", mobile)
                .eq("status", 1)
                .eq("consumed", 0)
                .orderByDesc("create_time")
                .last("limit 1").one();
    }

}
