package com.nb6868.xquick.modules.msg.dao;

import com.nb6868.xquick.booster.dao.BaseDao;
import com.nb6868.xquick.modules.msg.entity.SmsLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 短信发送记录
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Mapper
public interface SmsLogDao extends BaseDao<SmsLogEntity> {

}
