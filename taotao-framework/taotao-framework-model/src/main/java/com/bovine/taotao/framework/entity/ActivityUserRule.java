package com.bovine.taotao.framework.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bovine.taotao.common.mybatis.entity.CreateEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 活动新客规则实体类
 * @author eden
 * @date 2023/9/23 21:47:47
 */
@Getter
@Setter
@TableName(value = "tb_activity_user_rule")
public class ActivityUserRule extends CreateEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.INPUT)
    private Long id;

    /**
     * 注册时间
     */
    private Date registerTime;
}
