package model.support;

import javax.ws.rs.ext.Provider;

@Provider
public class BusinessException extends RuntimeException {
	public final ErrorCode code;

	public final Object extra;

	public final Throwable cause;

	public BusinessException(ErrorCode code, Object extra, Throwable cause) {
		this.code = code;
		this.extra = extra;
		this.cause = cause;
	}

	public BusinessException(ErrorCode code) {
		this(code, null, null);
	}
}
