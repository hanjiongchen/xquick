package com.nb6868.xquick.modules.uc.service.impl;

import com.nb6868.xquick.booster.service.impl.BaseServiceImpl;
import com.nb6868.xquick.modules.uc.dao.UserAppleDao;
import com.nb6868.xquick.modules.uc.entity.UserAppleEntity;
import com.nb6868.xquick.modules.uc.service.UserAppleService;
import org.springframework.stereotype.Service;

/**
 * 苹果用户
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class UserAppleServiceImpl extends BaseServiceImpl<UserAppleDao, UserAppleEntity> implements UserAppleService {

    @Override
    public UserAppleEntity getByUserIdentifier(String packageName, String userIdentifier) {
        return query().eq("package_name", packageName).eq("user_identifier", userIdentifier).last("limit 1").one();
    }
}
