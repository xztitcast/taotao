package com.bovine.taotao.setup.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bovine.taotao.common.mybatis.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 文章内容管理
 * @author eden
 * @date 2023年2月22日 下午6:31:46
 */
@Getter
@Setter
@TableName(value = "tb_classroom_content")
public class ClassroomContent extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@TableId(type = IdType.INPUT)
	private Long id;
	
	private String content;

	public ClassroomContent() {
		
	}
	
	public ClassroomContent(Long id, String content) {
		this.id = id;
		this.content = content;
	}
}
