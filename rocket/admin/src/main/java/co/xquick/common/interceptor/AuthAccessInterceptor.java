package co.xquick.common.interceptor;

import co.xquick.booster.exception.ErrorCode;
import co.xquick.booster.exception.XquickException;
import co.xquick.booster.util.HttpContextUtils;
import co.xquick.common.annotation.AnonAccess;
import co.xquick.modules.uc.UcConst;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限访问权限验证
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Component
public class AuthAccessInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        String token = HttpContextUtils.getRequestParameter(request, UcConst.TOKEN_HEADER);
        if (StringUtils.isEmpty(token)) {
            // 匿名访问
            AnonAccess annotation;
            if (handler instanceof HandlerMethod) {
                annotation = ((HandlerMethod) handler).getMethodAnnotation(AnonAccess.class);
            } else {
                return true;
            }

            if (annotation == null || !annotation.value()) {
                throw new XquickException(ErrorCode.UNAUTHORIZED);
            }
        }
        return true;
    }
}
