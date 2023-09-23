package com.bovine.taotao.setup.entity;

import java.io.Serializable;
import java.sql.Time;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bovine.taotao.common.mybatis.entity.CreateEntity;
import com.bovine.taotao.common.mybatis.entity.CreateEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 协议实体类
 * @author eden
 * @date 2023年2月12日 下午2:38:33
 */
@Getter
@Setter
@TableName(value = "tb_agreement")
public class Agreement extends CreateEntity<Integer> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 客服电话
	 */
	private String sphone;

	/**
	 * 公众号太阳码
	 */
	private String oasuncodepic;

	/**
	 * 客户服务协议
	 */
	private String csprotool;
	
	/**
	 * 入驻协议
	 */
	private String enterprotocl;
	
	/**
	 * 客服上班时间
	 */
	private Time stimestart;
	
	/**
	 * 客服下班时间
	 */
	private Time stimeend;
	
	/**
	 * 区域限制
	 */
	private String citylimitlist;
	
	/**
	 * 个人隐私协议
	 */
	private String privacyprotocol;
	
	/**
	 * 会员服务协议
	 */
	private String memberprotocol;
	
	/**
	 * 邮政储蓄银行电子服务协议
	 */
	private String psbcelectronicprotocol;
	
	/**
	 * 平台简介
	 */
	private String aboutpf;

	/**
	 * 中国邮政储蓄银行YOU商街授权协议
	 */
	private String authProtocl;

	/**
	 * 中国邮政储蓄银行开户隐私协议
	 */
	private String psbcPrivacyProtocol;
	
	/**
	 * 数字人民币服务协议
	 */
	private String numberWalletProtocol;
	
	/**
	 * 商户隐私协议
	 */
	private String merchantPrivacyProtocol;

}
