package com.bovine.taotao.setup.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bovine.taotao.common.mybatis.entity.CreateEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * "我的"页面实体类
 * @author eden
 * @date 2023年3月2日 下午7:59:11
 */
@Getter
@Setter
@TableName(value = "tb_mine")
public class Mine extends CreateEntity<Integer> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 顶部背景图
	 */
	private String topbox;
	
	/**
	 * 状态(是否删除 1:删除 0:正常)
	 */
	private Integer status;
	
	/**
	 * 常见问题是否展示(1是 2否)
	 */
	private Integer problem;
	
	/**
	 * 我的特权码是否展示(1是 2否)
	 */
	private Integer privilegeCode;
	
	/**
	 * 关于我们是否展示(1是 2否)
	 */
	private Integer aboutUs;
	
	/**
	 * 用户协议是否展示(1是 2否)
	 */
	private Integer agreement;
	
	/**
	 * 建议反馈是否展示(1是 2否)
	 */
	private Integer feedback;
	
	/**
	 * 会员中心是否展示(1是 2否)
	 */
	private Integer member;
	
	/**
	 * 客服微信是否展示(1是 2否)
	 */
	private Integer customer;
	
	/**
	 * 数字人民币服务是否展示(1是 2否)
	 */
	private Integer digital;
	
	/**
	 * 密码管理是否展示(1是 2否)
	 */
	private Integer password;
}
