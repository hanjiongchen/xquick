package co.xquick.common.interceptor;

import co.xquick.booster.exception.ErrorCode;
import co.xquick.booster.exception.XquickException;
import co.xquick.common.annotation.GuestAccess;
import co.xquick.modules.uc.UcConst;
import co.xquick.modules.uc.user.SecurityUser;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 游客访问权限验证
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Component
public class GuestAccessInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (UcConst.GUEST_TOKEN.equalsIgnoreCase(SecurityUser.getUser().getToken())) {
            // 游客
            GuestAccess annotation;
            if (handler instanceof HandlerMethod) {
                annotation = ((HandlerMethod) handler).getMethodAnnotation(GuestAccess.class);
            } else {
                return true;
            }

            if (annotation == null || !annotation.value()) {
                throw new XquickException(ErrorCode.GUEST_FORBIDDEN);
            }
        }
        return true;
    }
}
