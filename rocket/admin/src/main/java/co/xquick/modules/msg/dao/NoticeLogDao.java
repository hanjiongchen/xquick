package co.xquick.modules.msg.dao;

import co.xquick.booster.dao.BaseDao;
import co.xquick.modules.msg.entity.NoticeLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 通知发送记录
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Mapper
public interface NoticeLogDao extends BaseDao<NoticeLogEntity> {

}
