package co.xquick.modules.msg.dao;

import co.xquick.booster.dao.BaseDao;
import co.xquick.modules.msg.entity.SmsLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 短信发送记录
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Mapper
public interface SmsLogDao extends BaseDao<SmsLogEntity> {

}
