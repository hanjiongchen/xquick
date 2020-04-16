package com.nb6868.xquick.modules.sched.dao;

import com.nb6868.xquick.booster.dao.BaseDao;
import com.nb6868.xquick.modules.sched.entity.TaskEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 定时任务
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Mapper
public interface TaskDao extends BaseDao<TaskEntity> {

}
