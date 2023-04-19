package com.bovine.taotao.admin.web.view;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 后台管理系统前后端交互行政地区实体类
 * 构建select选择器需要的参数
 * @author eden
 * @date 2023年2月18日 下午3:20:30
 */
@Getter
@Setter
public class RegionView extends BaseSelectorView<String> {
	
	/**
	 * 子节点
	 */
	private List<RegionView> children = new ArrayList<>();
	
	public RegionView() {
		
	}
	
	public RegionView(String label, String value) {
		super(label, value);
	}
	
}
