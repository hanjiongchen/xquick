package co.xquick.modules.sys.dao;

import co.xquick.booster.dao.BaseDao;
import co.xquick.modules.sys.entity.CalendarEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 万年历
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Mapper
public interface CalendarDao extends BaseDao<CalendarEntity> {

}
