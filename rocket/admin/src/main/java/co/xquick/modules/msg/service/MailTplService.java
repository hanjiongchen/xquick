package co.xquick.modules.msg.service;

import co.xquick.booster.service.CrudService;
import co.xquick.modules.msg.dto.MailTplDTO;
import co.xquick.modules.msg.entity.MailTplEntity;

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
