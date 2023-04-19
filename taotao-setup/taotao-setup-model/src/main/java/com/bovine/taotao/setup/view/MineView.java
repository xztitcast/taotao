package com.bovine.taotao.setup.view;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * "我的"页面视图数据
 * @author eden
 * @date 2023年3月13日 下午5:00:09
 */
@Getter
@Setter
public class MineView implements Serializable {

	@Serial
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
	
	/**
	 * 区域
	 */
	private List<MineAreaView> viewList;
}
