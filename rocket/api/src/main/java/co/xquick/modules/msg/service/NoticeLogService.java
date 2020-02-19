package co.xquick.modules.msg.service;

import co.xquick.booster.service.CrudService;
import co.xquick.modules.msg.dto.NoticeLogDTO;
import co.xquick.modules.msg.entity.NoticeLogEntity;

/**
 * 通知发送记录
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public interface NoticeLogService extends CrudService<NoticeLogEntity, NoticeLogDTO> {

    boolean read(Long[] ids);

}
