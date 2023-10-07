package com.bovine.taotao.framework.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bovine.taotao.common.mybatis.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 支付认证策略表
 * </p>
 *
 * @author Eden
 * @since 2023-09-29
 */
@Getter
@Setter
@TableName("tb_activity_auth_strategy")
public class ActivityAuthStrategy extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 活动ID
     */
    private Long activityId;

    /**
     * 折扣价
     */
    private BigDecimal disPrice;

    /**
     * 原价
     */
    private BigDecimal costPrice;

    /**
     * 卡种
     */
    private String cardType;

}
