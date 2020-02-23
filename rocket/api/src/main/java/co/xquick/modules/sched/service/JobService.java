package co.xquick.modules.sched.service;

import co.xquick.booster.pojo.PageData;
import co.xquick.booster.service.BaseService;
import co.xquick.modules.sched.dto.JobDTO;
import co.xquick.modules.sched.entity.JobEntity;

import java.util.Map;

/**
 * 定时任务
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public interface JobService extends BaseService<JobEntity> {

	PageData<JobDTO> page(Map<String, Object> params);

	JobDTO get(Long id);

	/**
	 * 保存定时任务
	 */
	void save(JobDTO dto);
	
	/**
	 * 更新定时任务
	 */
	void update(JobDTO dto);
	
	/**
	 * 批量删除定时任务
	 */
	void deleteBatch(Long[] ids);
	
	/**
	 * 批量更新定时任务状态
	 */
	int updateBatch(Long[] ids, int status);
	
	/**
	 * 立即执行
	 */
	void run(Long[] ids);
	
	/**
	 * 暂停运行
	 */
	void pause(Long[] ids);
	
	/**
	 * 恢复运行
	 */
	void resume(Long[] ids);
}
