package com.bovine.taotao.common.redis.processor;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.autoproxy.AbstractBeanFactoryAwareAdvisingPostProcessor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.InitializingBean;

import com.bovine.taotao.common.redis.annotation.DbIndex;

public class RedisDbIndexSwitchProcessor extends AbstractBeanFactoryAwareAdvisingPostProcessor implements MethodInterceptor,InitializingBean {

	private static final long serialVersionUID = 1L;

	@Override
	public void afterPropertiesSet() throws Exception {
		AnnotationMatchingPointcut annotationMatchingPointcut = AnnotationMatchingPointcut.forMethodAnnotation(DbIndex.class);
		super.advisor = new DefaultPointcutAdvisor(annotationMatchingPointcut, this);
	}
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Method method = invocation.getMethod();
		DbIndex dbIndex = method.getAnnotation(DbIndex.class);
		try {
			RedisDbIndexThreadLocal.doSwitch(dbIndex.value());
			return invocation.proceed();
		} finally {
			RedisDbIndexThreadLocal.remove();
		}
	}

}
