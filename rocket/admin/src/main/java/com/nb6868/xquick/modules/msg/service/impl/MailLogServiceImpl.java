package com.nb6868.xquick.modules.msg.service.impl;

import com.nb6868.xquick.booster.service.impl.CrudServiceImpl;
import com.nb6868.xquick.modules.msg.dao.MailLogDao;
import com.nb6868.xquick.modules.msg.dto.MailLogDTO;
import com.nb6868.xquick.modules.msg.dto.MailSendRequest;
import com.nb6868.xquick.modules.msg.email.EmailUtils;
import com.nb6868.xquick.modules.msg.entity.MailLogEntity;
import com.nb6868.xquick.modules.msg.service.MailLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 邮件记录
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class MailLogServiceImpl extends CrudServiceImpl<MailLogDao, MailLogEntity, MailLogDTO> implements MailLogService {

    @Autowired
    private EmailUtils emailUtils;

    @Override
    public QueryWrapper<MailLogEntity> getWrapper(String method, Map<String, Object> params) {
        String tplId = (String) params.get("tplId");
        String mailTo = (String) params.get("mailTo");
        String status = (String) params.get("status");

        QueryWrapper<MailLogEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(tplId), "tpl_id", tplId);
        wrapper.like(StringUtils.isNotBlank(mailTo), "mail_to", mailTo);
        wrapper.eq(StringUtils.isNotBlank(status), "status", status);

        return wrapper;
    }

    /**
     * 发送短信
     */
    @Override
    public boolean send(MailSendRequest dto) {
        return emailUtils.sendMail(dto);
    }

}
