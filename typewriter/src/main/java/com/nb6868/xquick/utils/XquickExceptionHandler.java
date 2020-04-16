package com.nb6868.xquick.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@RestControllerAdvice
public class XquickExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(XquickException.class)
	public Result handleRenException(XquickException e){
		Result r = new Result();
		r.put("code", e.getCode());
		r.put("msg", e.getMessage());

		return r;
	}

	@ExceptionHandler(Exception.class)
	public Result handleException(Exception e){
		logger.error(e.getMessage(), e);
		return Result.error();
	}
}
