package co.xquick.modules.sched.service;

import co.xquick.booster.pojo.PageData;
import co.xquick.booster.service.BaseService;
import co.xquick.modules.sched.dto.TaskDTO;
import co.xquick.modules.sched.entity.TaskEntity;

import java.util.Map;

/**
 * 定时任务
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public interface TaskService extends BaseService<TaskEntity> {

	PageData<TaskDTO> page(Map<String, Object> params);

	TaskDTO get(Long id);

	/**
	 * 保存定时任务
	 */
	void save(TaskDTO dto);
	
	/**
	 * 更新定时任务
	 */
	void update(TaskDTO dto);
	
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
