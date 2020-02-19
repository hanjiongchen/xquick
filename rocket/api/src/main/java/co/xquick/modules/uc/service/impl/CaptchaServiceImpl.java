package co.xquick.modules.uc.service.impl;

import co.xquick.modules.uc.service.CaptchaService;
import com.google.code.kaptcha.Producer;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

/**
 * 验证码
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Autowired
    private Producer producer;

    /**
     * 本地缓存
     * 设置一个有效时间10分钟
     */
    Cache<String, String> localCache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterAccess(10, TimeUnit.MINUTES).build();

    @Override
    public BufferedImage create(String uuid) {
        // 生成文字验证码
        String code = producer.createText();
        // 保存到缓存
        setCache(uuid, code);

        return producer.createImage(code);
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
