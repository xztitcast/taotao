package com.bovine.taotao.common.core.exception.custom;

/**
 * @see 使用dubbo框架在service服务层定义自定义异常构造函数不能有枚举局部变量否则序列化失败
 * @author eden
 * @date 2023年2月7日 下午6:56:21
 */
public class AuthenticationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private int code;
	
	public AuthenticationException(int code, String message){
		this(code, message, null);
	}

	public AuthenticationException(int code, String message, Throwable cause){
		super(message, cause);
		this.code = code;
	}

	public int getCode(){
		return this.code;
	}

}
