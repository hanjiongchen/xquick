package com.nb6868.xquick.modules.sched.service;

import com.nb6868.xquick.booster.service.CrudService;
import com.nb6868.xquick.modules.sched.dto.TaskDTO;
import com.nb6868.xquick.modules.sched.entity.TaskEntity;

import java.util.List;

/**
 * 定时任务
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public interface TaskService extends CrudService<TaskEntity, TaskDTO> {

	/**
	 * 修改状态
	 */
	boolean changeStatus(List<Long> ids, int status);
	
	/**
	 * 立即执行
	 */
	void run(List<Long> ids);
	
	/**
	 * 暂停运行
	 */
	void pause(List<Long> ids);
	
	/**
	 * 恢复运行
	 */
	void resume(List<Long> ids);
}
