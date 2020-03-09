package co.xquick.modules.msg.dao;

import co.xquick.booster.dao.BaseDao;
import co.xquick.modules.msg.entity.SmsTplEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 短信模板
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Mapper
public interface SmsTplDao extends BaseDao<SmsTplEntity> {

}
