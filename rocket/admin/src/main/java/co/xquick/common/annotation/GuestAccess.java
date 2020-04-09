package co.xquick.common.annotation;

import java.lang.annotation.*;

/**
 * 是否允许游客访问
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GuestAccess {

    /**
     * 是否允许,默认允许
     */
    boolean value() default true;

}
