package com.nb6868.xquick.modules.msg.service;

import com.nb6868.xquick.booster.service.CrudService;
import com.nb6868.xquick.modules.msg.dto.SmsTplDTO;
import com.nb6868.xquick.modules.msg.entity.SmsTplEntity;

/**
 * 短信模板
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public interface SmsTplService extends CrudService<SmsTplEntity, SmsTplDTO> {

    /**
     * 通过编码获取模板
     * @param code 模板编码
     * @return 模板
     */
    SmsTplEntity getByCode(String code);

}
