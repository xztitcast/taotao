package com.bovine.taotao.framework.i.service;

import com.bovine.taotao.common.core.injecter.FrameworkService;
import com.bovine.taotao.framework.entity.ActivityRegionRule;

/**
 * 活动区域规则Service业务逻辑接口
 * @author eden
 * @date 2023/9/23 21:54:54
 */
public interface ActivityRegionRuleService extends FrameworkService<ActivityRegionRule, Long> {

    /**
     * spring bean名称
     */
    String BEAN_NAME = "activityRegionRuleService";
}
