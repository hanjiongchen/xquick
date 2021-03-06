package com.nb6868.xquick.modules.msg.dao;

import com.nb6868.xquick.booster.dao.BaseDao;
import com.nb6868.xquick.modules.msg.entity.MailLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 邮件发送记录
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Mapper
public interface MailLogDao extends BaseDao<MailLogEntity> {

}
