package com.bovine.taotao.admin.web.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bovine.taotao.common.core.serialize.SecretkeySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;

/**
 * 系统设置授权实体类
 * @author eden
 * @date 2023年2月22日 上午11:44:08
 */
@Getter
@Setter
@TableName(value = "tb_sys_device")
public class SysDevice implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@TableId(type = IdType.ASSIGN_ID)
	private Long id;
	
	/**
	 * AES 密钥
	 */
	@JsonSerialize(using = SecretkeySerializer.class)
	private String key;
	
	/**
	 * AES 向量
	 */
	@JsonSerialize(using = SecretkeySerializer.class)
	private String slat;
	
	/**
	 * 公司名称
	 */
	private Integer type;
	
	/**
	 * 关联结构ID
	 */
	private Long tisid;
	
	/**
	 * 关联结构名称
	 */
	private String tisname;
	
	/**
	 * 创建时间
	 */
	private Date created;
	
	/**
	 * 更新时间
	 */
	private Date updated;
	
}
