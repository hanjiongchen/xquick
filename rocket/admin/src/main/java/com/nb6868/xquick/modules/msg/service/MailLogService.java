package com.nb6868.xquick.modules.msg.service;

import com.nb6868.xquick.booster.service.CrudService;
import com.nb6868.xquick.modules.msg.dto.MailLogDTO;
import com.nb6868.xquick.modules.msg.dto.MailSendRequest;
import com.nb6868.xquick.modules.msg.entity.MailLogEntity;

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
    boolean send(MailSendRequest sendRequest);

}

