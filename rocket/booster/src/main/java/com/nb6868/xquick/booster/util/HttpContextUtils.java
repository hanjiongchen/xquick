package com.nb6868.xquick.booster.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * HttpContextUtils
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public class HttpContextUtils {

    private static Logger logger = LoggerFactory.getLogger(HttpContextUtils.class);

    public static HttpServletRequest getHttpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }

        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }

    public static Map<String, String> getParameterMap(HttpServletRequest request) {
        Enumeration<String> parameters = request.getParameterNames();

        Map<String, String> params = new HashMap<>();
        while (parameters.hasMoreElements()) {
            String parameter = parameters.nextElement();
            String value = request.getParameter(parameter);
            if (StringUtils.isNotBlank(value)) {
                params.put(parameter, value);
            }
        }

        return params;
    }

    public static String getDomain() {
        HttpServletRequest request = getHttpServletRequest();
        StringBuffer url = request.getRequestURL();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
    }

    public static String getOrigin() {
        HttpServletRequest request = getHttpServletRequest();
        return request.getHeader(HttpHeaders.ORIGIN);
    }

    public static String getLanguage() {
        //默认语言
        String defaultLanguage = "zh-CN";
        //request
        HttpServletRequest request = getHttpServletRequest();
        if (request == null) {
            return defaultLanguage;
        }

        //请求语言
        defaultLanguage = request.getHeader(HttpHeaders.ACCEPT_LANGUAGE);

        return defaultLanguage;
    }

    /**
     * 获取IP地址
     * <p>
     * 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
     * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        if (null == request) {
            return null;
        }
        String ip = null;
        String unknown = "unknown";
        try {
            ip = request.getHeader("x-forwarded-for");
            if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
                if ("127.0.0.1".equalsIgnoreCase(ip)) {
                    // 根据网卡取本机配置的IP
                    try {
                        ip = InetAddress.getLocalHost().getHostAddress();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ip != null && ip.length() > 15) {
                if (ip.indexOf(",") > 0) {
                    ip = ip.substring(0, ip.indexOf(","));
                }
            }
        } catch (Exception e) {
            logger.error("IPUtils ERROR ", e);
        }

        return ip;
    }

    public static String getName(HttpServletRequest request) {
        if (null == request) {
            return null;
        }
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取请求中的参数
     */
    public static String getRequestParameter(String name) {
        HttpServletRequest request = getHttpServletRequest();
        return getRequestParameter(request, name);
    }

    /**
     * 获取请求中的参数
     */
    public static String getRequestParameter(HttpServletRequest httpRequest, String name) {
        if (httpRequest == null || StringUtils.isEmpty(name)) {
            return null;
        }

        // 先从header中获取token
        String token = httpRequest.getHeader(name);

        // 如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = httpRequest.getParameter(name);
        }

        return token;
    }

    /**
     * 获取请求中的参数
     */
    public static String getRequestParameter(NativeWebRequest httpRequest, String name) {
        if (httpRequest == null || StringUtils.isEmpty(name)) {
            return null;
        }

        // 先从header中获取token
        String token = httpRequest.getHeader(name);

        // 如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = httpRequest.getParameter(name);
        }

        return token;
    }

}
