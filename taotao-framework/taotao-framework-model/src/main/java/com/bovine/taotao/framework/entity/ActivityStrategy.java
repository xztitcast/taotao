package com.bovine.taotao.framework.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bovine.taotao.common.mybatis.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 活动策略表
 * </p>
 *
 * @author Eden
 * @since 2023-09-29
 */
@Getter
@Setter
@TableName("tb_activity_strategy")
public class ActivityStrategy extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID(即活动ID)
     */
    private Long id;

    /**
     * 策略ID
     */
    private String strategyId;

}
