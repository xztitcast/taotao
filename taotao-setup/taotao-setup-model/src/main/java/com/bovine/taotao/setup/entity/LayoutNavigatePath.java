package com.bovine.taotao.setup.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * 导航路径实体类
 * @author eden
 * @date 2023年2月28日 下午4:48:18
 */
@Getter
@Setter
@TableName("tb_layout_navigate_path")
public class LayoutNavigatePath implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键ID
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	
	/**
	 * 导航名称
	 */
	private String navigateName;
	
	/**
	 * 导航路径
	 */
	private String navigatePath;

}
