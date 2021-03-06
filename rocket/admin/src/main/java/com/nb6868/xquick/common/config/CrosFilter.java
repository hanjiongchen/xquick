package com.nb6868.xquick.common.config;

import com.nb6868.xquick.booster.util.HttpContextUtils;

import javax.servlet.*;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * cros filter
 * 放行所有Options请求，提高接口访问速度
 *
 * @author Charles
 */
public class CrosFilter implements Filter {

    @Override
    public void init(FilterConfig config) {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            HttpServletResponse response = (HttpServletResponse) resp;
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Headers", "content-type,token");
            response.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
            // Access-Control-Allow-Origin和Access-Control-Allow-Credentials有约束
            // Credentials true,Origin必须指定具体来源,不能用*通配;
            // Credentials false,Origin可以用*通配;
            response.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, resp);
        }
    }

    @Override
    public void destroy() {
    }

}
