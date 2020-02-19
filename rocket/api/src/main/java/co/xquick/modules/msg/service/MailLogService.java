package co.xquick.modules.msg.service;

import co.xquick.booster.service.CrudService;
import co.xquick.modules.msg.dto.MailLogDTO;
import co.xquick.modules.msg.dto.MailSendRequestDTO;
import co.xquick.modules.msg.entity.MailLogEntity;

/**
 * 邮件发送记录
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public interface MailLogService extends CrudService<MailLogEntity, MailLogDTO> {

    /**
     * 发送邮件
     *
     * @param sendRequest 发送请求
     * @return 发送结果
     */
    boolean send(MailSendRequestDTO sendRequest);

}

