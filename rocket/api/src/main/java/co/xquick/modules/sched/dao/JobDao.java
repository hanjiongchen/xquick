package co.xquick.modules.sched.dao;

import co.xquick.booster.dao.BaseDao;
import co.xquick.modules.sched.entity.JobEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 定时任务
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Mapper
public interface JobDao extends BaseDao<JobEntity> {
	
	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);
}
