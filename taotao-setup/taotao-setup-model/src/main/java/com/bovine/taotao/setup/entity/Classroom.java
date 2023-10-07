package com.bovine.taotao.setup.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bovine.taotao.common.mybatis.entity.CreateEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 数币课堂实体类
 * @author eden
 * @date 2023年2月23日 下午7:17:32
 */
@Getter
@Setter
@TableName(value = "tb_classroom")
public class Classroom extends CreateEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.AUTO)
	private Long id;

	/**
	 * 发布人
	 */
	private String publisher;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 封面图
	 */
	private String pic;
	
	/**
	 * 封面图
	 */
	private String avatar;
	
	/**
	 * 是否置顶0:否 1:是
	 */
	private Integer top;
	
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
