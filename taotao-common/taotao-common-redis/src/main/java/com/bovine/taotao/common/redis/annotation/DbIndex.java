package com.bovine.taotao.common.redis.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * redis DB 索引
 * @author eden
 * @date 2023年3月5日 下午12:19:47
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface DbIndex {

	int value() default 0;
}
