package com.bovine.taotao.common.core.exception.custom;

import com.bovine.taotao.common.core.S;

/**
 * 自定义系统模块业务异常
 * 自定义异常必须集成RuntimeException
 * @author eden
 * @date 2022年10月23日 上午10:07:37
 */
public class SysServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private S s;

	public SysServiceException(S s, Throwable cause) {
		super(s.getMessage(), cause);
	}

	public SysServiceException(S s) {
		this(s, null);
	}
	public SysServiceException(String message) {
		super(message);
	}
	
	public S getS() {
		return this.s;
	}
	
}
