package com.bovine.taotao.setup.model;

import com.bovine.taotao.common.core.BaseModel;
import com.bovine.taotao.common.core.serialize.IntegerIdKeyDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Getter;
import lombok.Setter;

/**
 * 小程序前后端交互 查询文章数据的查询条件
 * 当类目ID为空、置顶为空、热门为空时查全部
 * @author eden
 * @date 2023年2月23日 下午4:48:52
 */
@Getter
@Setter
public class ArticleAndClassroomModel extends BaseModel {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 文章类目ID
	 */
	@JsonDeserialize(using = IntegerIdKeyDeserializer.class)
	private Integer categoryId;
	
	/**
	 * 热门
	 */
	private Integer hot;

}
