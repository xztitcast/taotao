package com.bovine.taotao.setup.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bovine.taotao.common.mybatis.entity.TissueEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 文章实体类
 * 文章初版暂时放入核心模块内应付初版上线、后续迁移到设置中心setup模块
 * @author eden
 * @date 2023年2月22日 下午6:20:00
 */
@Getter
@Setter
@TableName(value = "tb_article")
public class Article extends TissueEntity<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 作者
	 */
	private String author;
	
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
	 * 状态1:待发布 2:已发布 3:已下架
	 */
	private Integer status;
	
	/**
	 * 类目ID
	 */
	private Integer categoryId;
	
	/**
	 * 发布时间
	 */
	private Date pushTime;
	
	@TableField(exist = false)
	private String content;
	
}
