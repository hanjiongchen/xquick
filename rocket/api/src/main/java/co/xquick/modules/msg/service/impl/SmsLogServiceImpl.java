package co.xquick.modules.msg.service.impl;

import co.xquick.booster.exception.XquickException;
import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.booster.util.ConvertUtils;
import co.xquick.booster.util.ParamUtils;
import co.xquick.modules.msg.dao.SmsLogDao;
import co.xquick.modules.msg.dto.SmsLogDTO;
import co.xquick.modules.msg.dto.SmsSendRequestDTO;
import co.xquick.modules.msg.dto.SmsTplDTO;
import co.xquick.modules.msg.entity.SmsLogEntity;
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
    public void send(SmsSendRequestDTO dto) {
        // 获得template
        SmsTplDTO smsTemplate = smsTemplateService.getByCode(dto.getTplCode());
        if (null == smsTemplate) {
            throw new XquickException("找不到对应的短信模板");
        }

        // 获取短信服务
        AbstractSmsService service = SmsFactory.build(smsTemplate.getPlatform());

        // 发送短信
        service.sendSms(smsTemplate, dto.getMobile(), dto.getParam());
    }

    @Override
    public SmsLogDTO findLastLog(Long templateId, String mobile) {
        return ConvertUtils.sourceToTarget(baseMapper.selectOne(new QueryWrapper<SmsLogEntity>()
                .eq("tpl_id", templateId)
                .eq("mobile", mobile)
                .eq("status", 1)
                .orderByDesc("create_time")
                .last("limit 1")), SmsLogDTO.class);
    }

    @Override
    public SmsLogDTO findLastLogByTplCode(String tplCode, String mobile) {
        return ConvertUtils.sourceToTarget(baseMapper.selectOne(new QueryWrapper<SmsLogEntity>()
                .eq("tpl_code", tplCode)
                .eq("mobile", mobile)
                .eq("status", 1)
                .eq("consumed", 0)
                .orderByDesc("create_time")
                .last("limit 1")), SmsLogDTO.class);
    }

}
