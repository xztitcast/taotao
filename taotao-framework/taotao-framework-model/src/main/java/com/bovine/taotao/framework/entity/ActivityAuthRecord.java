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
@TableName("tb_activity_auth_record")
public class ActivityAuthRecord extends BaseEntity implements Serializable {

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
     * 活动名称(冗余字段)
     */
    private String activityName;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户手机号(冗余字段)
     */
    private String mobile;

    /**
     * 折扣价
     */
    private BigDecimal disPrice;

    /**
     * 原价
     */
    private BigDecimal costPrice;

    /**
     * 状态 1:待支付 2:已完成 3:失败 4:超时 5:退款
     */
    private Short status;

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 流水号
     */
    private String trxId;

    /**
     * 卡种
     */
    private String cardType;

    /**
     * 机构号
     */
    private Long tisid;

}
