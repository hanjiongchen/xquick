package co.xquick.common.annotation;

import java.lang.annotation.*;

/**
 * 是否要求登录注解
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Login {

}
