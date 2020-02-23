package co.xquick.modules.sched.service.impl;

import co.xquick.booster.constant.Constant;
import co.xquick.booster.pojo.PageData;
import co.xquick.booster.service.impl.BaseServiceImpl;
import co.xquick.booster.util.ConvertUtils;
import co.xquick.modules.sched.dao.TaskDao;
import co.xquick.modules.sched.dto.TaskDTO;
import co.xquick.modules.sched.entity.TaskEntity;
import co.xquick.modules.sched.service.TaskService;
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
public class TaskServiceImpl extends BaseServiceImpl<TaskDao, TaskEntity> implements TaskService {
	@Autowired
	private Scheduler scheduler;

	@Override
	public PageData<TaskDTO> page(Map<String, Object> params) {
		IPage<TaskEntity> page = baseMapper.selectPage(
			getPage(params, Constant.CREATE_DATE, false),
			getWrapper(params)
		);
		return getPageData(page, TaskDTO.class);
	}

	@Override
	public TaskDTO get(Long id) {
		TaskEntity entity = baseMapper.selectById(id);

		return ConvertUtils.sourceToTarget(entity, TaskDTO.class);
	}

	private QueryWrapper<TaskEntity> getWrapper(Map<String, Object> params){
		String beanName = (String)params.get("beanName");

		QueryWrapper<TaskEntity> wrapper = new QueryWrapper<>();
		wrapper.like(StringUtils.isNotBlank(beanName), "bean_name", beanName);

		return wrapper;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void save(TaskDTO dto) {
		TaskEntity entity = ConvertUtils.sourceToTarget(dto, TaskEntity.class);

		entity.setStatus(Constant.ScheduleStatus.NORMAL.getValue());
        this.save(entity);
        
        ScheduleUtils.createScheduleJob(scheduler, entity);
    }
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(TaskDTO dto) {
		TaskEntity entity = ConvertUtils.sourceToTarget(dto, TaskEntity.class);

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