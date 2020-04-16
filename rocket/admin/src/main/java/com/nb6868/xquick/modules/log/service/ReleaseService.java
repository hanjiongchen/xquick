package com.nb6868.xquick.modules.log.service;

import com.nb6868.xquick.booster.service.CrudService;
import com.nb6868.xquick.modules.log.dto.ReleaseDTO;
import com.nb6868.xquick.modules.log.entity.ReleaseEntity;

/**
 * 更新日志
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public interface ReleaseService extends CrudService<ReleaseEntity, ReleaseDTO> {


    /**
     * 通过code获取最新的release
     *
     * @param code 查询code
     * @return 结果
     */
    ReleaseDTO getLatestByCode(String code);

}
