package com.bovine.taotao.framework.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bovine.taotao.common.core.Constant;
import com.bovine.taotao.common.mybatis.entity.CreateEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 活动次数规则实体类
 * @author eden
 * @date 2023/9/23 22:04:04
 */
@Getter
@Setter
@TableName(value = "tb_activity_unlimit_rule")
public class ActivityUnlimitRule extends CreateEntity<Long> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 总次数
     */
    private Integer total;

    /**
     * 每月次数
     */
    private Integer month;

    /**
     * 每周次数
     */
    private Integer week;

    /**
     * 每天次数
     */
    private Integer day;

    /**
     * 获取属性值
     * @param feild 属性名
     * @return
     */
    public Integer getUnlimit(String feild) {
        return switch (feild){
            case Constant.TOTAL -> this.total;
            case Constant.MONTH -> this.month;
            case Constant.WEEK -> this.week;
            case Constant.DAY -> this.day;
            default -> null;
        };
    }
}
