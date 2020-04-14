package co.xquick.modules.log.service;

import co.xquick.booster.service.CrudService;
import co.xquick.modules.log.dto.ReleaseDTO;
import co.xquick.modules.log.entity.ReleaseEntity;

import java.io.Serializable;

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
