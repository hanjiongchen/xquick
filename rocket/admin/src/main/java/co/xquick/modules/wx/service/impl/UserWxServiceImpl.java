package co.xquick.modules.wx.service.impl;

import co.xquick.booster.service.impl.BaseServiceImpl;
import co.xquick.modules.wx.dao.UserWxDao;
import co.xquick.modules.wx.entity.UserWxEntity;
import co.xquick.modules.wx.service.UserWxService;
import org.springframework.stereotype.Service;

/**
 * 微信用户
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class UserWxServiceImpl extends BaseServiceImpl<UserWxDao, UserWxEntity> implements UserWxService {

    @Override
    public UserWxEntity getByAppIdAndOpenId(String appId, String openId) {
        return query().eq("app_id", appId).eq("open_id", openId).last("LIMIT 1").one();
    }
}
