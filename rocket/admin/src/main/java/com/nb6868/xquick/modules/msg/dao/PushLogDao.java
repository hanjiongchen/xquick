package com.nb6868.xquick.modules.msg.dao;

import com.nb6868.xquick.booster.dao.BaseDao;
import com.nb6868.xquick.modules.msg.entity.PushLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 消息推送记录
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Mapper
public interface PushLogDao extends BaseDao<PushLogEntity> {

}
