package com.nb6868.xquick.modules.wx.dao;

import com.nb6868.xquick.booster.dao.BaseDao;
import com.nb6868.xquick.modules.wx.entity.UserWxEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 微信用户
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Mapper
public interface UserWxDao extends BaseDao<UserWxEntity> {

}
