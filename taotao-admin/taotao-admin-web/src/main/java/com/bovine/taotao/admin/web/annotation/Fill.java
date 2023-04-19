package com.bovine.taotao.admin.web.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 填充
 * @author eden
 * @date 2023年2月18日 下午6:21:42
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface Fill {

	/**
	 * 填充类型
	 * @return
	 */
	FillType value();
}
