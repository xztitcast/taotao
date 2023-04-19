package com.bovine.taotao.setup.model;

import java.io.Serial;

import com.bovine.taotao.common.core.BaseModel;

import lombok.Getter;
import lombok.Setter;

/**
 * 后台管理系统 查询导航库table列表数据的查询条件
 * smart-admin-web -> SysModuleController -> list()
 * @author eden
 * @date 2023年3月13日 下午6:34:23
 */
@Getter
@Setter
public class SysModuleModel extends BaseModel {
	
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 模块名
	 */
	private String name;
}
