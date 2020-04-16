package com.nb6868.xquick.modules.msg.service;

import com.nb6868.xquick.booster.service.CrudService;
import com.nb6868.xquick.modules.msg.dto.SmsLogDTO;
import com.nb6868.xquick.modules.msg.dto.SmsSendRequest;
import com.nb6868.xquick.modules.msg.entity.SmsLogEntity;

import java.io.Serializable;

/**
 * 短信发送记录
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public interface SmsLogService extends CrudService<SmsLogEntity, SmsLogDTO> {

    /**
     * 发送短信
     */
    void send(SmsSendRequest smsSendRequest);

    /**
     * 消费短信
     */
    void consumeById(Serializable id);

    /**
     * 通过模板id和手机号找最后一次发送记录
     * @param tplId
     * @param mobile
     * @return
     */
    SmsLogEntity findLastLog(Long tplId, String mobile);

    /**
     * 通过模板编码和手机号找最后一次发送记录
     */
    SmsLogEntity findLastLogByTplCode(String tplCode, String mobile);

}
