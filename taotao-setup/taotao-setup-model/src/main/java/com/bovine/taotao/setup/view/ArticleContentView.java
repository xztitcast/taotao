package com.bovine.taotao.setup.view;

import java.io.Serial;

import lombok.Getter;
import lombok.Setter;

/**
 * 小程序交互 文章内容视图数据实体类
 * @author eden
 * @date 2023年2月23日 下午6:07:49
 */
@Getter
@Setter
public class ArticleContentView extends ArticleView {

	@Serial
	private static final long serialVersionUID = 1L;
	
	/**
	 * 作者
	 */
	private String author;
	
	/**
	 * 内容
	 */
	private String content;

}
