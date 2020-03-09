package co.xquick.modules.msg.dao;

import co.xquick.booster.dao.BaseDao;
import co.xquick.modules.msg.entity.PushLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 消息推送记录
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Mapper
public interface PushLogDao extends BaseDao<PushLogEntity> {

}
