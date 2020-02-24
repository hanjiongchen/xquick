package co.xquick.modules.sched.dao;

import co.xquick.booster.dao.BaseDao;
import co.xquick.modules.sched.entity.TaskLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 定时任务日志
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Mapper
public interface TaskLogDao extends BaseDao<TaskLogEntity> {
	
}