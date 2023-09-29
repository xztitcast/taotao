package com.bovine.taotao.admin.web.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bovine.taotao.common.mybatis.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@TableName(value = "tb_sys_user")
public class SysUser extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.AUTO)
	private Long id;

    private String username;

    @JSONField(serialize = false)
    private String password;

    @JSONField(serialize = false)
    private String salt;

    private Integer status;
    
    private String avatar;
    
    private Long creator;
    
    /**
     * 冗余机构ID
     */
    private Long tisid;
    
    /**
     * 冗余机构名称
     */
    private String tisname;

	/**
	 * 锁定账号时间(可以是Long值或设置成Date值)
	 */
	private Date locked;
    
    @TableField(exist = false)
    private Integer parentId = 0;
    
    @TableField(exist = false)
    private List<Long> roleIdList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

	@JsonIgnore
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	public Long getCreator() {
		return creator;
	}
	
	public void setCreator(Long creator) {
		this.creator = creator;
	}
	
	public Long getTisid() {
		return tisid;
	}

	public void setTisid(Long tisid) {
		this.tisid = tisid;
	}

	public String getTisname() {
		return tisname;
	}

	public void setTisname(String tisname) {
		this.tisname = tisname;
	}
	
	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public List<Long> getRoleIdList() {
		return roleIdList;
	}

	public void setRoleIdList(List<Long> roleIdList) {
		this.roleIdList = roleIdList;
	}

	public Date getLocked() {
		return locked;
	}

	public void setLocked(Date locked) {
		this.locked = locked;
	}
}