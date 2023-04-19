package com.bovine.taotao.common.security.annotation;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.bovine.taotao.common.security.entity.Entity;

@Documented
@Retention(RUNTIME)
@Target(PARAMETER)
public @interface Subject {

	Entity value() default Entity.ID;
}
