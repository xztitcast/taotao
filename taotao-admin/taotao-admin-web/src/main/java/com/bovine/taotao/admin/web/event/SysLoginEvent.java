package com.bovine.taotao.admin.web.event;

import org.springframework.context.ApplicationEvent;

/**
 * 系统登录事件源
 * @author eden
 * @date 2022年11月16日 下午9:50:20
 */
public class SysLoginEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	public SysLoginEvent(Object source) {
		super(source);
	}

}
