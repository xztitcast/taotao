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
 * 活动白名单规则实体类
 * @author eden
 * @date 2023/9/23 21:58:58
 */
@Getter
@Setter
@TableName(value = "tb_activity_whitelist_rule")
public class ActivityWhitelistRule extends CreateEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 关联活动ID
     */
    private Long activityId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户手机号
     */
    private String mobile;

    /**
     * 有效至
     */
    private Date expireAt;
}
