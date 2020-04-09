package co.xquick.modules.wx.service;

import co.xquick.booster.service.BaseService;
import co.xquick.modules.wx.entity.UserWxEntity;

/**
 * 微信用户
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public interface UserWxService extends BaseService<UserWxEntity> {

    /**
     * 通过appid和openId获得wx用户
     * @param appId
     * @param openId
     * @return
     */
    UserWxEntity getByAppIdAndOpenId(String appId, String openId);

}
