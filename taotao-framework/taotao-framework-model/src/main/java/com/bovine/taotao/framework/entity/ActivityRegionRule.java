package com.bovine.taotao.framework.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bovine.taotao.common.mybatis.entity.CreateEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 活动区域规则实体类
 * @author eden
 * @date 2023/9/23 21:52:52
 */
@Getter
@Setter
@TableName(value = "tb_activity_region_rule")
public class ActivityRegionRule extends CreateEntity<Long> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 区域列表
     */
    private List<String> regionList;
}
