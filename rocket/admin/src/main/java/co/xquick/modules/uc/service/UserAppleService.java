package co.xquick.modules.uc.service;

import co.xquick.booster.service.BaseService;
import co.xquick.modules.uc.entity.UserAppleEntity;

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
