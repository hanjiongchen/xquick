package co.xquick.modules.msg.dao;

import co.xquick.booster.dao.BaseDao;
import co.xquick.modules.msg.entity.MailTplEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 邮件模板
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Mapper
public interface MailTplDao extends BaseDao<MailTplEntity> {

}
