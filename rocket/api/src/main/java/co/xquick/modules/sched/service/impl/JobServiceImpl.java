package co.xquick.modules.sched.service.impl;

import co.xquick.booster.constant.Constant;
import co.xquick.booster.pojo.PageData;
import co.xquick.booster.service.impl.BaseServiceImpl;
import co.xquick.booster.util.ConvertUtils;
import co.xquick.modules.sched.dao.JobDao;
import co.xquick.modules.sched.dto.JobDTO;
import co.xquick.modules.sched.entity.JobEntity;
import co.xquick.modules.sched.service.JobService;
import co.xquick.modules.sched.utils.ScheduleUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class JobServiceImpl extends BaseServiceImpl<JobDao, JobEntity> implements JobService {
	@Autowired
	private Scheduler scheduler;

	@Override
	public PageData<JobDTO> page(Map<String, Object> params) {
		IPage<JobEntity> page = baseMapper.selectPage(
			getPage(params, Constant.CREATE_DATE, false),
			getWrapper(params)
		);
		return getPageData(page, JobDTO.class);
	}

	@Override
	public JobDTO get(Long id) {
		JobEntity entity = baseMapper.selectById(id);

		return ConvertUtils.sourceToTarget(entity, JobDTO.class);
	}

	private QueryWrapper<JobEntity> getWrapper(Map<String, Object> params){
		String beanName = (String)params.get("beanName");

		QueryWrapper<JobEntity> wrapper = new QueryWrapper<>();
		wrapper.like(StringUtils.isNotBlank(beanName), "bean_name", beanName);

		return wrapper;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void save(JobDTO dto) {
		JobEntity entity = ConvertUtils.sourceToTarget(dto, JobEntity.class);

		entity.setStatus(Constant.ScheduleStatus.NORMAL.getValue());
        this.save(entity);
        
        ScheduleUtils.createScheduleJob(scheduler, entity);
    }
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(JobDTO dto) {
		JobEntity entity = ConvertUtils.sourceToTarget(dto, JobEntity.class);

        ScheduleUtils.updateScheduleJob(scheduler, entity);
                
        this.updateById(entity);
    }

	@Override
	@Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] ids) {
    	for(Long id : ids){
    		ScheduleUtils.deleteScheduleJob(scheduler, id);
    	}
    	
    	//删除数据
    	this.logicDeleteByIds(Arrays.asList(ids));
	}

	@Override
    public int updateBatch(Long[] ids, int status){
    	Map<String, Object> map = new HashMap<>(2);
    	map.put("ids", ids);
    	map.put("status", status);
    	return baseMapper.updateBatch(map);
    }
    
	@Override
	@Transactional(rollbackFor = Exception.class)
    public void run(Long[] ids) {
    	for(Long id : ids){
    		ScheduleUtils.run(scheduler, this.getById(id));
    	}
    }

	@Override
	@Transactional(rollbackFor = Exception.class)
    public void pause(Long[] ids) {
        for(Long id : ids){
    		ScheduleUtils.pauseJob(scheduler, id);
    	}
        
    	updateBatch(ids, Constant.ScheduleStatus.PAUSE.getValue());
    }

	@Override
	@Transactional(rollbackFor = Exception.class)
    public void resume(Long[] ids) {
    	for(Long id : ids){
    		ScheduleUtils.resumeJob(scheduler, id);
    	}

    	updateBatch(ids, Constant.ScheduleStatus.NORMAL.getValue());
    }
    
}