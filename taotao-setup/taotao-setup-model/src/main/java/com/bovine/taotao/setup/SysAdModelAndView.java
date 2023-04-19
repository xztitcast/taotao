package com.bovine.taotao.setup;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 广告内容视图数据
 * @author eden
 * @date 2023年3月1日 下午7:57:42
 */
@Getter
@Setter
public class SysAdModelAndView implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long adId;
	
	private List<SysAdJoinContentModelAndView> joinViewList;
	
	/**
	 * 关联广告内容视图
	 * @author eden
	 * @date 2023年3月1日 下午9:14:09
	 */
	@Getter
	@Setter
	public static class SysAdJoinContentModelAndView implements Serializable {
		
		@Serial
		private static final long serialVersionUID = 1L;

		/**
		 * 广告内容ID
		 */
		private Long adContentId;
		
		/**
		 * 排序
		 */
		private Integer sorted;
		
		public SysAdJoinContentModelAndView() {
			
		}
		
		public SysAdJoinContentModelAndView(Long adContentId, Integer sorted) {
			this.adContentId = adContentId;
			this.sorted = sorted;
		}
	}
	
	public SysAdModelAndView() {
		
	}
	
	public SysAdModelAndView(Long adId, List<SysAdJoinContentModelAndView> joinViewList) {
		this.adId = adId;
		this.joinViewList = joinViewList;
	}
}
