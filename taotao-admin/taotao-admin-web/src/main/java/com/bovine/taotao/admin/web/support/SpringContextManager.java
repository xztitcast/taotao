package com.bovine.taotao.admin.web.support;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

public abstract class SpringContextManager {

	private static ApplicationContext applicationContext;;
	
	/**
	 * 根据类型获取对象
	 * @param cls 
	 * @return T
	 */
	public static <T> T getBean(Class<T> cls) {
		return applicationContext.getBean(cls);
	}
	
	/**
	 * 根据id名称和类型获取对象
	 * @param name
	 * @param cls
	 * @return
	 */
	public static <T> T getBean(String name, Class<T> cls) {
		return applicationContext.getBean(name, cls);
	}
	
	public static <T> T getBean(Class<T> requiredType, Object... args) {
		return applicationContext.getBean(requiredType, args);
	}
	
	/**
	 * 根据id名称获取对象
	 * @param name
	 * @return
	 */
	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}
	
	public static boolean containsBean(String name) {
		return applicationContext.containsBean(name);
	}

	public static boolean isSingleton(String name) {
		return applicationContext.isSingleton(name);
	}
	
	@Component
	public static class ApplicationContextHolder implements ApplicationContextAware {

		@Override
		public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
			SpringContextManager.applicationContext = applicationContext;
		}

	}
}
