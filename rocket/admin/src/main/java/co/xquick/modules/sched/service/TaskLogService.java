package co.xquick.modules.sched.service;

import co.xquick.booster.pojo.PageData;
import co.xquick.booster.service.BaseService;
import co.xquick.modules.sched.dto.TaskLogDTO;
import co.xquick.modules.sched.entity.TaskLogEntity;

import java.util.Map;

/**
 * 定时任务日志
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public interface TaskLogService extends BaseService<TaskLogEntity> {

	PageData<TaskLogDTO> page(Map<String, Object> params);

	TaskLogDTO get(Long id);
}
