package com.bovine.taotao.admin.web.modelAndView.view;

import lombok.Getter;
import lombok.Setter;

/**
 * 后台管理系统 选择器视图数据
 * @author eden
 * @date 2023年3月1日 下午7:29:09
 */
@Getter
@Setter
public class BaseSelectorView<T> {

	/**
	 * 选择器label值
	 */
	protected String label;
	
	/**
	 * 选择器label -> value 值
	 */
	protected T value;
	
	/**
	 * 是否禁用
	 */
	protected boolean disabled = false;
	
	public BaseSelectorView() {
		
	}
	
	public BaseSelectorView(String label, T value) {
		this.label = label;
		this.value = value;
	}
}
