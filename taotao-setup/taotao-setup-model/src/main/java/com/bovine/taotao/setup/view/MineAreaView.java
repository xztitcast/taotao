package com.bovine.taotao.setup.view;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import com.bovine.taotao.common.core.ModelAndView;

import lombok.Getter;
import lombok.Setter;

/**
 * 我的页面区域视图数据
 * @author eden
 * @date 2023年3月2日 下午10:12:53
 */
@Getter
@Setter
public class MineAreaView implements Serializable {
	
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 区域id
	 */
	private Long id;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 关联我的页面模块数据列表
	 */
	private List<MineModuleView> minModuleList;
	
	public MineAreaView() {
		
	}
	
	public MineAreaView(Long id, String title) {
		this.id = id;
		this.title = title;
	}
	
	public MineAreaView(Long id, String title, List<MineModuleView> minModuleList) {
		this.id = id;
		this.title = title;
		this.minModuleList = minModuleList;
	}

	/**
	 * 我的页面模块视图数据
	 * @author eden
	 * @date 2023年3月2日 下午10:11:21
	 */
	@Getter
	@Setter
	public static class MineModuleView extends ModelAndView<Long> implements Serializable {

		private static final long serialVersionUID = 1L;
		
		/**
		 * 模块名称
		 */
		private String name;
		
		/**
		 * 图标
		 */
		private String icon;
		
		/**
		 * 页面路径
		 */
		private String url;
		
		public MineModuleView() {
			
		}
		
		public MineModuleView(Long id, String name, String icon, String url) {
			this.id = id;
			this.name = name;
			this.icon = icon;
			this.url = url;
		}
	}
}
