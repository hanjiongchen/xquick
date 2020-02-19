package co.xquick.common.aspect;

import co.xquick.common.annotation.LogOperation;
import co.xquick.booster.util.HttpContextUtils;
import co.xquick.booster.util.JacksonUtils;
import co.xquick.modules.log.LogConst.*;
import co.xquick.modules.log.entity.OperationEntity;
import co.xquick.modules.log.service.OperationService;
import co.xquick.modules.uc.user.SecurityUser;
import co.xquick.modules.uc.user.UserDetail;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 操作日志，切面处理类
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Aspect
@Component
public class LogOperationAspect {

    @Autowired
    private OperationService logOperationService;

    @Pointcut("@annotation(co.xquick.common.annotation.LogOperation)")
    public void pointcut() { }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        try {
            //执行方法
            Object result = point.proceed();
            //执行时长(毫秒)
            long time = System.currentTimeMillis() - beginTime;
            //保存日志
            saveLog(point, time, OperationStatusEnum.SUCCESS.value());
            return result;
        } catch (Exception e) {
            //执行时长(毫秒)
            long time = System.currentTimeMillis() - beginTime;
            //保存日志
            saveLog(point, time, OperationStatusEnum.FAIL.value());
            throw e;
        }
    }

    private void saveLog(ProceedingJoinPoint joinPoint, long time, Integer status) throws Exception {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = joinPoint.getTarget().getClass().getDeclaredMethod(signature.getName(), signature.getParameterTypes());
        LogOperation annotation = method.getAnnotation(LogOperation.class);

        OperationEntity log = new OperationEntity();

        if (annotation != null) {
            // 注解上的描述
            log.setOperation(annotation.value());
        }

        //登录用户信息
        UserDetail user = SecurityUser.getUser();
        log.setCreateName(user.getUsername());
        log.setStatus(status);
        log.setRequestTime(time);

        //请求相关信息
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        log.setIp(HttpContextUtils.getIpAddr(request));
        if (null != request) {
            log.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
            log.setUri(request.getRequestURI());
            log.setMethod(request.getMethod());
        }

        //请求参数
        Object[] args = joinPoint.getArgs();
        try {
            String params = JacksonUtils.pojoToJson(args[0]);
            log.setParams(params);
        } catch (Exception e) {

        }

        // 保存到DB
        logOperationService.save(log);
    }
}
