package com.nb6868.xquick.modules.uc.service;

import com.nb6868.xquick.booster.service.BaseService;
import com.nb6868.xquick.modules.uc.entity.UserAppleEntity;

/**
 * 苹果用户
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public interface UserAppleService extends BaseService<UserAppleEntity> {

    /**
     * 通过包名和apple user id找到用户记录
     */
    UserAppleEntity getByUserIdentifier(String packageName, String userIdentifier);

}
