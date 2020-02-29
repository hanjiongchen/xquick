package co.xquick.modules.msg.service;

import co.xquick.booster.service.CrudService;
import co.xquick.modules.msg.dto.SmsLogDTO;
import co.xquick.modules.msg.dto.SmsSendRequestDTO;
import co.xquick.modules.msg.entity.SmsLogEntity;

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
    void send(SmsSendRequestDTO smsSendRequest);

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
    SmsLogDTO findLastLog(Long tplId, String mobile);

    /**
     * 通过模板编码和手机号找最后一次发送记录
     * @param tplCode
     * @param mobile
     * @return
     */
    SmsLogDTO findLastLogByTplCode(String tplCode, String mobile);

}
