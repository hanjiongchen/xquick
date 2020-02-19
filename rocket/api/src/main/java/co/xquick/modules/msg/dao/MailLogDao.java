package co.xquick.modules.msg.dao;

import co.xquick.booster.dao.BaseDao;
import co.xquick.modules.msg.entity.MailLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 邮件发送记录
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Mapper
public interface MailLogDao extends BaseDao<MailLogEntity> {

}
