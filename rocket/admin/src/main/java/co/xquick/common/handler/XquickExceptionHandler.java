package co.xquick.common.handler;

import cn.hutool.core.map.MapUtil;
import co.xquick.booster.exception.ErrorCode;
import co.xquick.booster.exception.XquickException;
import co.xquick.booster.pojo.Result;
import co.xquick.booster.util.ExceptionUtils;
import co.xquick.booster.util.HttpContextUtils;
import co.xquick.booster.util.JacksonUtils;
import co.xquick.modules.log.entity.ErrorEntity;
import co.xquick.modules.log.service.ErrorService;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 异常处理器
 *
 * @author Charles (zhancgchaoxu@gmail.com)
 */
@RestControllerAdvice
public class XquickExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(XquickExceptionHandler.class);

    @Autowired
    private ErrorService sysLogErrorService;

    /**
     * 处理自定义异常
     *
     * * @param e exception
     * @return result
     */
    @ExceptionHandler(XquickException.class)
    public Result<?> handleXquickException(XquickException e) {
        return new Result<>().error(e.getCode(), e.getMsg());
    }

    /**
     * 处理主键重复异常
     *
     * @param e exception
     * @return result
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public Result<?> handleDuplicateKeyException(DuplicateKeyException e) {
        return new Result<>().error(ErrorCode.DB_RECORD_EXISTS);
    }

    /**
     * 处理方法不支持异常
     *
     * @param e exception
     * @return result
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<?> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return new Result<>().error(ErrorCode.METHOD_NOT_ALLOWED);
    }

    /**
     * 处理参数错误
     *
     * @param e exception
     * @return result
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result<?> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return new Result<>().error(ErrorCode.ERROR_REQUEST);
    }

    /**
     * 处理404
     *
     * @param e exception
     * @return result
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public Result<?> handleNoHandlerFoundException(HttpServletResponse response, NoHandlerFoundException e) {
        // 解决跨域问题
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());
        return new Result<>().error(ErrorCode.NOT_FOUND);
    }

    /**
     * 处理Shiro未授权异常
     *
     * @param e exception
     * @return result
     */
    @ExceptionHandler(UnauthorizedException.class)
    public Result<?> handleNoHandlerFoundException(UnauthorizedException e) {
        return new Result<>().error(ErrorCode.FORBIDDEN);
    }

    /**
     * 处理授权失败异常
     *
     * @param e exception
     * @return result
     */
    @ExceptionHandler(UnauthenticatedException.class)
    public Result<?> handleUnauthenticatedExceptionException(UnauthenticatedException e) {
        return new Result<>().error(ErrorCode.UNAUTHORIZED);
    }

    /**
     * 数据库结构异常
     *
     * @param e exception
     * @return result
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public Result<?> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        logger.error(e.getMessage(), e);
        // 保存日志
        saveLog(e);
        return new Result<>().error(ErrorCode.DB_VIOLATION_ERROR);
    }

    /**
     * spring默认上传大小100MB 超出大小捕获异常MaxUploadSizeExceededException
     *
     * @param e exception
     * @return result
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Result<?> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        logger.error(e.getMessage(), e);
        // 保存日志
        saveLog(e);
        return new Result<>().error(ErrorCode.DB_VIOLATION_ERROR);
    }

    /**
     * 处理其它异常
     *
     * @param e exception
     * @return result
     */
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        logger.error(e.getMessage(), e);
        // 保存日志
        saveLog(e);
        return new Result<>().error();
    }

    /**
     * 保存异常日志
     */
    private void saveLog(Exception ex) {
        ErrorEntity log = new ErrorEntity();

        // 请求相关信息
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        if (null != request) {
            log.setIp(HttpContextUtils.getIpAddr(request));
            log.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
            log.setUri(request.getRequestURI());
            log.setMethod(request.getMethod());
            Map<String, String> params = HttpContextUtils.getParameterMap(request);
            if (MapUtil.isNotEmpty(params)) {
                log.setParams(JacksonUtils.pojoToJson(params));
            }
        }
        // 异常信息
        log.setContent(ExceptionUtils.getErrorStackTrace(ex));

        //保存
        sysLogErrorService.save(log);
    }
}
