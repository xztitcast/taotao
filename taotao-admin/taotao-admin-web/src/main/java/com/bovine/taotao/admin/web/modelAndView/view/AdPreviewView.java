package com.bovine.taotao.admin.web.modelAndView.view;

import lombok.Getter;
import lombok.Setter;

/**
 * 广告预览视图数据
 * @author eden
 * @date 2023年3月2日 上午10:56:29
 */
@Getter
@Setter
public class AdPreviewView {

	/**
	 * 广告内容id
	 */
	private Long id;
	
	/**
	 * 广告内容图片
	 */
	private String pics;
	
	public AdPreviewView() {
		
	}
	
	public AdPreviewView(Long id, String pics) {
		this.id = id;
		this.pics = pics;
	}
}
