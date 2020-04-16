package com.nb6868.xquick.modules.wx.service;

import com.nb6868.xquick.booster.service.BaseService;
import com.nb6868.xquick.modules.wx.entity.UserWxEntity;

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
