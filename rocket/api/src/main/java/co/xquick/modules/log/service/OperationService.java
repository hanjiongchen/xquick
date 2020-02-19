package co.xquick.modules.log.service;

import co.xquick.booster.service.CrudService;
import co.xquick.modules.log.dto.OperationDTO;
import co.xquick.modules.log.entity.OperationEntity;

/**
 * 操作日志
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
public interface OperationService extends CrudService<OperationEntity, OperationDTO> {

}
