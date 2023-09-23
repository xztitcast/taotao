package com.bovine.taotao.setup.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bovine.taotao.common.mybatis.entity.CreateEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 广告内容信息实体类
 * @author eden
 * @date 2023年2月11日 下午4:19:01
 */
@Getter
@Setter
@TableName(value = "tb_ad_content")
public class AdContent extends CreateEntity<Long> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 广告内容名称
	 */
	private String name;
	
	/**
	 * 广告类目ID
	 */
	private Long categoryId;
	
	/**
	 * 广告类目名称
	 */
	private String categoryName;
	
	/**
	 * 广告类目参数
	 */
	private String categoryParam;


	/**
	 * 链接地址
	 */
	private String urls;


	/**
	 * 广告图片地址
	 */
	private String pics;
	
	/**
	 * 跳转类型 1:内部小程序  2:外部小程序 3:H5
	 */
	private Integer jumpType;
	
	/**
	 * 跳转外部小程序appid
	 */
	private String jumpAppid;
	
	/**
	 * 跳转路径
	 */
	private String jumpUrl;
	
	/**
	 * 是否可分享(0:否 1:是)
	 */
	private Integer shared;
	
	/**
	 * 开始时间
	 */
	private Date startTime;
	
	/**
	 * 结束时间
	 */
	private Date endTime;

}
