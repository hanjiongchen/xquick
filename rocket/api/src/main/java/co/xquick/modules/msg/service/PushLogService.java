package co.xquick.modules.msg.service;

import co.xquick.booster.service.CrudService;
import co.xquick.modules.msg.dto.PushLogDTO;
import co.xquick.modules.msg.entity.PushLogEntity;

/**
 * 消息推送记录
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public interface PushLogService extends CrudService<PushLogEntity, PushLogDTO> {

}
