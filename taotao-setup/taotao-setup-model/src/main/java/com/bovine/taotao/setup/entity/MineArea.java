package com.bovine.taotao.setup.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bovine.taotao.common.mybatis.entity.CreateEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * "我的"页面区域实体类
 * @author eden
 * @date 2023年3月2日 下午8:06:29
 */
@Getter
@Setter
@TableName(value = "tb_mine_area")
public class MineArea extends CreateEntity<Long> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 关联我的页面ID
	 */
	private Integer mineId;

	/**
	 * 区域标题
	 */
	private String title;
	
	/**
	 * 状态(是否失效 1:失效 0:正常)
	 */
	private Integer status;

}
