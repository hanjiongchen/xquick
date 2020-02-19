package co.xquick.booster.exception;

import co.xquick.booster.util.MessageUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义异常
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode( callSuper = false)
public class XquickException extends RuntimeException {

	private static final long serialVersionUID = 1L;

    private int code;
	private String msg;

	public XquickException(int code) {
		this.code = code;
		this.msg = MessageUtils.getMessage(code);
	}

	public XquickException(int code, String... params) {
		this.code = code;
		this.msg = MessageUtils.getMessage(code, params);
	}

	public XquickException(int code, Throwable e) {
		super(e);
		this.code = code;
		this.msg = MessageUtils.getMessage(code);
	}

	public XquickException(int code, Throwable e, String... params) {
		super(e);
		this.code = code;
		this.msg = MessageUtils.getMessage(code, params);
	}

	public XquickException(String msg) {
		super(msg);
		this.code = ErrorCode.INTERNAL_SERVER_ERROR;
		this.msg = msg;
	}

	public XquickException(String msg, Throwable e) {
		super(msg, e);
		this.code = ErrorCode.INTERNAL_SERVER_ERROR;
		this.msg = msg;
	}

}
