package com.nb6868.xquick.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 接口参数加密，切面处理类
 * todo 待实现
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Aspect
@Component
public class ApiEncryptAspect {

    @Pointcut("@annotation(co.xquick.common.annotation.ApiEncrypt)")
    public void pointcut() { }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        // 执行方法
        return point.proceed();
    }
}
