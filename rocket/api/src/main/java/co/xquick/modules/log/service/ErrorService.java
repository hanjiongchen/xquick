package co.xquick.modules.log.service;

import co.xquick.booster.service.CrudService;
import co.xquick.modules.log.dto.ErrorDTO;
import co.xquick.modules.log.entity.ErrorEntity;

/**
 * 异常日志
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
public interface ErrorService extends CrudService<ErrorEntity, ErrorDTO> {

}
