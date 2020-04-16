package com.nb6868.xquick.common.aspect;

import com.nb6868.xquick.booster.util.HttpContextUtils;
import com.nb6868.xquick.booster.util.JacksonUtils;
import com.nb6868.xquick.common.annotation.LogOperation;
import com.nb6868.xquick.modules.log.entity.OperationEntity;
import com.nb6868.xquick.modules.log.service.OperationService;
import com.nb6868.xquick.modules.uc.user.SecurityUser;
import com.nb6868.xquick.modules.uc.user.UserDetail;
import com.nb6868.xquick.modules.log.LogConst;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

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

    @Pointcut("@annotation(com.nb6868.xquick.common.annotation.LogOperation)")
    public void pointcut() { }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 记录开始执行时间
        long beginTime = System.currentTimeMillis();
        // 需要先把param拿出来,不然processed以后可能会被修改赋值
        String requestParam = getRequestParam(joinPoint);
        try {
            // 执行方法
            Object result = joinPoint.proceed();
            // 保存日志
            saveLog(joinPoint, requestParam, System.currentTimeMillis() - beginTime, LogConst.OperationStatusEnum.SUCCESS.value());
            return result;
        } catch (Exception e) {
            //保存日志
            saveLog(joinPoint, requestParam, System.currentTimeMillis() - beginTime, LogConst.OperationStatusEnum.FAIL.value());
            throw e;
        }
    }

    private void saveLog(ProceedingJoinPoint joinPoint,  String requestParam, long time, Integer status) throws NoSuchMethodException {
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

        log.setParams(requestParam);
        // 保存到DB
        logOperationService.save(log);
    }

    /**
     * 从joinPoint获取参数
     */
    private String getRequestParam(ProceedingJoinPoint joinPoint) {
        // 请求参数,接口方法中的参数,可能会有HttpServletRequest
        Object[] args = joinPoint.getArgs();
        List<Object> actualParam = new ArrayList<>();
        for (Object arg : args) {
            if (!(arg instanceof ServletRequest) && !(arg instanceof ServletResponse)) {
                actualParam.add(arg);
            }
        }
        if (actualParam.size() == 1) {
            return JacksonUtils.pojoToJson(actualParam.get(0));
        } else if  (actualParam.size() > 1) {
            return JacksonUtils.pojoToJson(actualParam);
        } else {
            return null;
        }
    }

}
