package co.xquick.modules.uc.shiro;

import co.xquick.booster.exception.ErrorCode;
import co.xquick.booster.pojo.Result;
import co.xquick.booster.util.HttpContextUtils;
import co.xquick.booster.util.JacksonUtils;
import co.xquick.modules.uc.UcConst;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Oauth2 过滤器
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
public class Oauth2Filter extends AuthenticatingFilter {

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        // 获取请求token
        String token = HttpContextUtils.getRequestParameter((HttpServletRequest) request, UcConst.TOKEN_HEADER);
        return StringUtils.isBlank(token) ? null : new Oauth2Token(token);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        // 放行所有OPTIONS请求
        return RequestMethod.OPTIONS.name().equals(((HttpServletRequest) request).getMethod());
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        // 获取请求token，如果token不存在，直接返回401
        String token = HttpContextUtils.getRequestParameter((HttpServletRequest) request, UcConst.TOKEN_HEADER);
        if (StringUtils.isBlank(token)) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setContentType("application/json;charset=utf-8");
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());

            String json = JacksonUtils.pojoToJson(new Result<>().error(ErrorCode.UNAUTHORIZED));
            httpResponse.getWriter().print(json);

            return false;
        }

        // 会调用createToken
        return executeLogin(request, response);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());
        try {
            // 处理登录失败的异常
            Throwable throwable = e.getCause() == null ? e : e.getCause();
            String json = JacksonUtils.pojoToJson(new Result<>().error(ErrorCode.UNAUTHORIZED, throwable.getMessage()));
            httpResponse.getWriter().print(json);
        } catch (IOException e1) {

        }
        return false;
    }

}
