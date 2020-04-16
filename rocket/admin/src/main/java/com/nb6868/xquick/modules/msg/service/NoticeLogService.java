package com.nb6868.xquick.modules.msg.service;

import com.nb6868.xquick.booster.service.CrudService;
import com.nb6868.xquick.modules.msg.dto.NoticeLogDTO;
import com.nb6868.xquick.modules.msg.entity.NoticeLogEntity;

/**
 * 通知发送记录
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public interface NoticeLogService extends CrudService<NoticeLogEntity, NoticeLogDTO> {

    boolean read(Long[] ids);

}
