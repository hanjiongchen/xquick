package co.xquick.modules.uc.service;

import java.awt.image.BufferedImage;

/**
 * 验证码
 *
 * @author Charles
 */
public interface CaptchaService {

    /**
     * 生成图片验证码
     * @param uuid
     * @return 生成的图片
     */
    BufferedImage create(String uuid);

    /**
     * 效验验证码
     * @param uuid  uuid
     * @param code  验证码
     * @return  true：成功  false：失败
     */
    boolean validate(String uuid, String code);
}
