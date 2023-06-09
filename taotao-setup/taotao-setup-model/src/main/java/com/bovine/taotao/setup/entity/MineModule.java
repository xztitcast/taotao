package com.bovine.taotao.setup.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bovine.taotao.common.mybatis.entity.TissueEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * "我的"页面模块实体类
 * @author eden
 * @date 2023年3月2日 下午8:04:39
 */
@Getter
@Setter
@TableName(value = "tb_mine_module")
public class MineModule extends TissueEntity<Long> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 模块名称
	 */
	private String name;
	
	/**
	 * 模块图标
	 */
	private String icon;
	
	/**
	 * 模块页面路径
	 */
	private String url;
	
	/**
	 * 状态是否删除 1:删除 0:正常
	 * 
	 * 删除规则大于是否使用规则, 当状态为删除时该调数据不查询出来, 当状态为正常使用规则为否时前端不显示
	 */
	private Integer status;

}
