package com.bovine.taotao.admin.web.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bovine.taotao.common.mybatis.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 数据字典实体类
 * @author eden
 * @date 2022/10/30 10:07:07
 */
@Getter
@Setter
@TableName("tb_sys_dict")
public class SysDict extends BaseEntity<Long> {

    @TableField(value = "key")
    private String key;

    @TableField(value = "value")
    private String value;

    private String remark;
}
