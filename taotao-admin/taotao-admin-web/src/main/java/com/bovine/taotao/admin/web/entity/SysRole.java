package com.bovine.taotao.admin.web.entity;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bovine.taotao.common.mybatis.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName(value = "tb_sys_role")
public class SysRole extends BaseEntity<Long> implements Serializable {
	
	private static final long serialVersionUID = 1L;

    private String name;

    private String remark;

    private Long creator;

    @TableField(exist = false)
    private List<Long> menuIdList;

}