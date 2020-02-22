/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package co.xquick.modules.qrtz.service;

import co.xquick.booster.pojo.PageData;
import co.xquick.booster.service.BaseService;
import co.xquick.modules.qrtz.dto.ScheduleJobLogDTO;
import co.xquick.modules.qrtz.entity.ScheduleJobLogEntity;

import java.util.Map;

/**
 * 定时任务日志
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface ScheduleJobLogService extends BaseService<ScheduleJobLogEntity> {

	PageData<ScheduleJobLogDTO> page(Map<String, Object> params);

	ScheduleJobLogDTO get(Long id);
}
