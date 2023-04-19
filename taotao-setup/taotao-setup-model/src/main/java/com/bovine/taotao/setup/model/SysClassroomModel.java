package com.bovine.taotao.setup.model;

import java.io.Serial;

import com.bovine.taotao.common.core.BaseModel;

import lombok.Getter;
import lombok.Setter;

/**
 * 后台管理系统 查询课堂table列表数据的查询条件
 * smart-admin-web -> SysArticleController -> list()
 * @author eden
 * @date 2023年2月22日 下午8:54:06
 */
@Getter
@Setter
public class SysClassroomModel extends BaseModel {

	@Serial
	private static final long serialVersionUID = 1L;
	
	/**
	 * 文章名称
	 */
	private String title;
	
	/**
	 * 发布人
	 */
	private String publisher;
	
	/**
	 * 状态1:待发布 2:已发布 3:已下架
	 */
	private String status;

}
