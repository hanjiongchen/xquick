package co.xquick.modules.sched.service.impl;

import co.xquick.booster.constant.Constant;
import co.xquick.booster.pojo.PageData;
import co.xquick.booster.service.impl.BaseServiceImpl;
import co.xquick.booster.util.ConvertUtils;
import co.xquick.modules.sched.dao.JobLogDao;
import co.xquick.modules.sched.dto.JobLogDTO;
import co.xquick.modules.sched.entity.JobLogEntity;
import co.xquick.modules.sched.service.JobLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class JobLogServiceImpl extends BaseServiceImpl<JobLogDao, JobLogEntity> implements JobLogService {

	@Override
	public PageData<JobLogDTO> page(Map<String, Object> params) {
		IPage<JobLogEntity> page = baseMapper.selectPage(
			getPage(params, Constant.CREATE_DATE, false),
			getWrapper(params)
		);
		return getPageData(page, JobLogDTO.class);
	}

	private QueryWrapper<JobLogEntity> getWrapper(Map<String, Object> params){
		String jobId = (String)params.get("jobId");

		QueryWrapper<JobLogEntity> wrapper = new QueryWrapper<>();
		wrapper.eq(StringUtils.isNotBlank(jobId), "job_id", jobId);

		return wrapper;
	}

	@Override
	public JobLogDTO get(Long id) {
		JobLogEntity entity = baseMapper.selectById(id);

		return ConvertUtils.sourceToTarget(entity, JobLogDTO.class);
	}

}