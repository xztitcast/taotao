package com.bovine.taotao.setup.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * 文章内容管理
 * @author eden
 * @date 2023年2月22日 下午6:31:46
 */
@Getter
@Setter
@TableName(value = "tb_classroom_content")
public class ClassroomContent implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@TableId(type = IdType.INPUT)
	private Long id;
	
	private String content;
	
	@TableField(fill = FieldFill.INSERT)
	protected Date created;
	
	/**
	 * 更新时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	protected Date updated;

	public ClassroomContent() {
		
	}
	
	public ClassroomContent(Long id, String content) {
		this.id = id;
		this.content = content;
	}
}
