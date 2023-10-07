package com.bovine.taotao.setup.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bovine.taotao.common.mybatis.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * "我的"页面区域模块关联实体类
 * @author eden
 * @date 2023年3月3日 下午6:14:51
 */
@Getter
@Setter
@TableName(value = "tb_mine_module_area")
public class MineModuleArea extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.AUTO)
	private Long id;
	
	/**
	 * 模块ID
	 */
	private Long moduleId;
	
	/**
	 * 区域ID
	 */
	private Long areaId;
	
	public MineModuleArea() {
		
	}
	
	public MineModuleArea(Long areaId) {
		this.areaId = areaId;
	}

}
