package com.bovine.taotao.setup.view;

import java.io.Serializable;
import java.util.Date;

import com.bovine.taotao.common.core.ModelAndView;

import lombok.Getter;
import lombok.Setter;

/**
 * 小程序前后端交互 文章视图数据实体类
 * @author eden
 * @date 2023年2月23日 下午5:06:29
 */
@Getter
@Setter
public class ArticleView extends ModelAndView<Long> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 封面图
	 */
	private String pic;
	
	/**
	 * 是否置顶0:否 1:是
	 */
	private Integer top;
	
	/**
	 * 是否热门0:否 1:是
	 */
	private Integer hot;
	
	/**
	 * 发布时间
	 */
	private Date pushTime;
	
	/**
	 * 内容
	 */
	private String content;
}
