package com.bovine.taotao.setup.view;

import java.io.Serial;
import java.io.Serializable;

import com.bovine.taotao.common.core.serialize.IntegerIdKeySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;

/**
 * 小程序交互 文章类目视图数据实体类
 * @author eden
 * @date 2023年2月23日 下午5:39:59
 */
@Getter
@Setter
public class CategoryView implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 类目ID
	 */
	@JsonSerialize(using = IntegerIdKeySerializer.class)
	private Integer id;
	
	/**
	 * 类目名称
	 */
	private String name;
	
	public CategoryView() {
		
	}
	
	public CategoryView(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
}
