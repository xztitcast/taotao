package com.bovine.taotao.framework.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bovine.taotao.common.mybatis.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 玩法策略表
 * </p>
 *
 * @author Eden
 * @since 2023-09-29
 */
@Getter
@Setter
@TableName("tb_strategy")
public class Strategy extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID(即spring bean name)
     */
    private String id;

    /**
     * 策略名称
     */
    private String name;

    /**
     * 状态 0:无效 1:有效
     */
    private Short status;

}
