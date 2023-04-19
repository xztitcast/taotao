package com.bovine.taotao.setup.view;

import java.io.Serializable;
import java.util.Date;

import com.bovine.taotao.common.core.ModelAndView;

import lombok.Getter;
import lombok.Setter;

/**
 * 小程序前后端交互 数币课堂视图数据实体类
 * @author eden
 * @date 2023年2月23日 下午9:10:09
 */
@Getter
@Setter
public class ClassroomView extends ModelAndView<Long> implements Serializable {

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
	 * 头像
	 */
	private String avatar;
	
	/**
	 * 是否置顶0:否 1:是
	 */
	private Integer top;
	
	/**
	 * 发布时间
	 */
	private Date pushTime;
	
	/**
	 * 发布人
	 */
	private String publisher;
}
