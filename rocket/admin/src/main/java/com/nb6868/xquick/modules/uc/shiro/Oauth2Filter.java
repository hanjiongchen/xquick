package com.nb6868.xquick.modules.uc.shiro;

import com.nb6868.xquick.booster.exception.ErrorCode;
import com.nb6868.xquick.booster.pojo.Result;
import com.nb6868.xquick.booster.util.HttpContextUtils;
import com.nb6868.xquick.booster.util.JacksonUtils;
import com.nb6868.xquick.modules.uc.UcConst;
import lombok.SneakyThrows;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Oauth2 过滤器
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
public class Oauth2Filter extends AuthenticatingFilter {

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        // 获取请求token
        final String token = HttpContextUtils.getRequestParameter((HttpServletRequest) request, UcConst.TOKEN_HEADER);
        return new AuthenticationToken() {
            @Override
            public String getPrincipal() {
                return StringUtils.isEmpty(token) ? UcConst.TOKEN_ANON : token;
            }

            @Override
            public String getCredentials() {
                return StringUtils.isEmpty(token) ? UcConst.TOKEN_ANON : token;
            }
        };
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        // 放行所有OPTIONS请求
        return RequestMethod.OPTIONS.name().equals(((HttpServletRequest) request).getMethod());
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        // 会调用createToken
        return executeLogin(request, response);
    }

    /**
     * 登录失败
     *
     * Oauth2Realm.doGetAuthenticationInfo抛出的异常会在这里捕获处理
     */
    @SneakyThrows
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());
        // 处理登录失败的异常
        Throwable throwable = e.getCause() == null ? e : e.getCause();
        String json = JacksonUtils.pojoToJson(new Result<>().error(ErrorCode.UNAUTHORIZED, throwable.getMessage()));
        httpResponse.getWriter().print(json);
        return false;
    }

}
