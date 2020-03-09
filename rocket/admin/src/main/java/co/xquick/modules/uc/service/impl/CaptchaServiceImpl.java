package co.xquick.modules.uc.service.impl;

import co.xquick.modules.uc.service.CaptchaService;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wf.captcha.SpecCaptcha;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 验证码
 * [SpecCaptcha](https://github.com/whvcse/EasyCaptcha)
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {
    /**
     * 本地缓存
     * 设置一个有效时间10分钟
     */
    Cache<String, String> localCache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterAccess(10, TimeUnit.MINUTES).build();

    @Override
    public String createBase64(String uuid) {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        String code = specCaptcha.text().toLowerCase();
        // 保存到缓存
        setCache(uuid, code);
        return specCaptcha.toBase64();
    }

    /**
     * 校验验证码
     * @param uuid  uuid
     * @param code  验证码
     * @return 验证是否成功
     */
    @Override
    public boolean validate(String uuid, String code) {
        // 获取验证码
        String captcha = getCache(uuid);
        // 效验成功
        return code.equalsIgnoreCase(captcha);
    }

    private void setCache(String key, String value) {
        localCache.put(key, value);
    }

    private String getCache(String key) {
        String captcha = localCache.getIfPresent(key);
        // 删除验证码
        if (captcha != null) {
            localCache.invalidate(key);
        }
        return captcha;
    }
}
