package co.xquick.modules.wx.dao;

import co.xquick.booster.dao.BaseDao;
import co.xquick.modules.wx.entity.UserWxEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 微信用户
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Mapper
public interface UserWxDao extends BaseDao<UserWxEntity> {

}
