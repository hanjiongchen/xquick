/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package co.xquick.modules.qrtz.service.impl;

import co.xquick.booster.constant.Constant;
import co.xquick.booster.pojo.PageData;
import co.xquick.booster.service.impl.BaseServiceImpl;
import co.xquick.booster.util.ConvertUtils;
import co.xquick.modules.qrtz.dao.ScheduleJobLogDao;
import co.xquick.modules.qrtz.dto.ScheduleJobLogDTO;
import co.xquick.modules.qrtz.entity.ScheduleJobLogEntity;
import co.xquick.modules.qrtz.service.ScheduleJobLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ScheduleJobLogServiceImpl extends BaseServiceImpl<ScheduleJobLogDao, ScheduleJobLogEntity> implements ScheduleJobLogService {

	@Override
	public PageData<ScheduleJobLogDTO> page(Map<String, Object> params) {
		IPage<ScheduleJobLogEntity> page = baseMapper.selectPage(
			getPage(params, Constant.CREATE_DATE, false),
			getWrapper(params)
		);
		return getPageData(page, ScheduleJobLogDTO.class);
	}

	private QueryWrapper<ScheduleJobLogEntity> getWrapper(Map<String, Object> params){
		String jobId = (String)params.get("jobId");

		QueryWrapper<ScheduleJobLogEntity> wrapper = new QueryWrapper<>();
		wrapper.eq(StringUtils.isNotBlank(jobId), "job_id", jobId);

		return wrapper;
	}

	@Override
	public ScheduleJobLogDTO get(Long id) {
		ScheduleJobLogEntity entity = baseMapper.selectById(id);

		return ConvertUtils.sourceToTarget(entity, ScheduleJobLogDTO.class);
	}

}