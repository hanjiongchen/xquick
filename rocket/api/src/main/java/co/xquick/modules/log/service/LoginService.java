package co.xquick.modules.log.service;

import co.xquick.booster.service.CrudService;
import co.xquick.modules.log.dto.LoginDTO;
import co.xquick.modules.log.entity.LoginEntity;

/**
 * 登录日志
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
public interface LoginService extends CrudService<LoginEntity, LoginDTO> {

}
