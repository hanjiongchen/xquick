package co.xquick.common.annotation;

import java.lang.annotation.*;

/**
 * 接口 请求参数和返回内容加密
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiEncrypt {

    /**
     * 入参是否解密，默认解密
     */
    boolean in() default true;

    /**
     * 出参是否加密，默认不加密
     */
    boolean out() default false;

}
