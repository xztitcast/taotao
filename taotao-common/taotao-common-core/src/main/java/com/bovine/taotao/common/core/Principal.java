package com.bovine.taotao.common.core;

import lombok.Getter;
import lombok.Setter;
import org.apache.dubbo.config.annotation.DubboService;

import java.io.Serializable;
import java.util.Date;

/**
 * 缓存实体对象
 */
@Getter
@Setter
public class Principal implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	private Long id;

	/**
	 * 机构ID
	 */
	private Long tisid;

	/**
	 * 用户名(即手机号)
	 */
	private String mobile;

	/**
	 * 昵称
	 */
	private String nickname;

	/**
	 * 微信openid
	 */
	private String openid;

	/**
	 * 微信unionid
	 */
	private String unionid;

	/**
	 * 用户标识ID
	 */
	private String userpasid;

	/**
	 * 用户类型(1:真实用户 2:虚拟用户)
	 */
	private Integer type;

	/**
	 * 省份
	 */
	private String pname;

	/**
	 * 城市
	 */
	private String cname;

	/**
	 * 地区
	 */
	private String areaname;

	/**
	 * 省ID
	 */
	private String pid;

	/**
	 * 城市ID
	 */
	private String cid;

	/**
	 * 地区ID
	 */
	private String areaId;

	/**
	 * 国家
	 */
	private String country;

	/**
	 * 创建时间即注册时间
	 */
	private Date created;

	/**
	 * 头像
	 */
	private String avatar;

	public Principal() {
		super();
	}

	public Principal(Long id, String mobile, String openid) {
		super();
		this.id = id;
		this.mobile = mobile;
		this.openid = openid;
	}

	public Principal(Long id, String openid) {
		super();
		this.id = id;
		this.openid = openid;
	}

	/**
	 * 获取用户省、市、区
	 * @return
	 */
	public String getLocation() {
		if(this.pname == null || this.cname == null || this.areaname == null) {
			return null;
		}
		return this.pname.concat(this.cname).concat(this.areaname);
	}

	/**
	 * 获取用户省、市、区 ID
	 * @return
	 */
	public String getLocationId() {
		if(this.pid == null || this.cid == null || this.areaId == null) {
			return null;
		}
		return this.pid.concat(Constant.DELIMITER_COMMA).concat(this.cid).concat(Constant.DELIMITER_COMMA).concat(this.areaId);
	}


}
