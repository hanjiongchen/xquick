package co.xquick.modules.sched.dao;

import co.xquick.booster.dao.BaseDao;
import co.xquick.modules.sched.entity.TaskEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 定时任务
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Mapper
public interface TaskDao extends BaseDao<TaskEntity> {
	
	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);
}
