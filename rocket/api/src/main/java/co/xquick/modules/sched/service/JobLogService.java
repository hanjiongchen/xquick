package co.xquick.modules.sched.service;

import co.xquick.booster.pojo.PageData;
import co.xquick.booster.service.BaseService;
import co.xquick.modules.sched.dto.JobLogDTO;
import co.xquick.modules.sched.entity.JobLogEntity;

import java.util.Map;

/**
 * 定时任务日志
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public interface JobLogService extends BaseService<JobLogEntity> {

	PageData<JobLogDTO> page(Map<String, Object> params);

	JobLogDTO get(Long id);
}
