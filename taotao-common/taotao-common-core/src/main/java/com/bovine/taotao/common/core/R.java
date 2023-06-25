package com.bovine.taotao.common.core;

import com.alibaba.fastjson2.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class R extends JSONObject {

	private static final long serialVersionUID = 1L;

	private static final String CODE = "code";
	
	private static final String MESSAGE = "msg";
	
	private static final String RESULT = "result";
	
	public R() {
		this(S.SUCCESS.getValue(), S.SUCCESS.getMessage());
	}
	
	public R(int code, String message) {
		put(CODE, code);
		put(MESSAGE, message);
	}

	public static R error() {
		return error(S.ERROR);
	}
	
	public static R error(String message) {
		return error(S.ERROR.getValue(), message);
	}
	
	public static R error(S s) {
		return error(s.getValue(), s.getMessage());
	}
	
	public static R error(int code, String message) {
		R r = new R(code, message);
		return r;
	}
	
	public static R ok() {
		return new R();
	}
	
	public static R ok(Object value) {
		return ok().put(RESULT, value);
	}
	
	public R put(Object value) {
		super.put(RESULT, value);
		return this;
	}
	
	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}
	
	public R ofAll(Map<String, Object> ofAll) {
		super.putAll(ofAll);
		return this;
	}
	
	public Object get() {
		return super.get(RESULT);
	}
}
