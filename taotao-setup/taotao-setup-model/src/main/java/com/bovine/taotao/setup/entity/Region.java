package com.bovine.taotao.setup.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bovine.taotao.common.mybatis.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 全国行政区实体类
 * @author eden
 * @date 2023年2月18日 下午2:28:21
 */
@Getter
@Setter
@TableName(value = "tb_region")
public class Region extends BaseEntity<Long> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 省份、城市、区县编号
	 */
	private String cid;
	
	/**
	 * 省份、城市、区县名称
	 */
	private String cname;
	
	/**
	 * 父级省份、城市、区县编号
	 */
	private String pid;
	
	/**
	 * 父级省份、城市、区县名称
	 */
	private String pname;
	
	/**
	 * 地区id(关联查询时使用)
	 */
	@TableField(exist = false)
	private String areaId;
	
	/**
	 * 地区名称(关联查询时使用)
	 */
	@TableField(exist = false)
	private String areaname;
	
	/**
	 * 状态 0:无效 1:有效
	 */
	private Integer status;

}
