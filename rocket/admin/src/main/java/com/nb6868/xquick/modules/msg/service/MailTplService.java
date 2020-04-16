package com.nb6868.xquick.modules.msg.service;

import com.nb6868.xquick.booster.service.CrudService;
import com.nb6868.xquick.modules.msg.dto.MailTplDTO;
import com.nb6868.xquick.modules.msg.entity.MailTplEntity;

/**
 * 邮件模板
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public interface MailTplService extends CrudService<MailTplEntity, MailTplDTO> {

    /**
     * 通过编码获取模板
     * @param code 模板编码
     * @return 模板
     */
    MailTplEntity getByCode(String code);

}
