package com.bovine.taotao.framework.i.service;

import com.bovine.taotao.common.core.injecter.FrameworkService;
import com.bovine.taotao.framework.entity.ActivityWhitelistRule;

/**
 * 活动白名单Service业务逻辑接口
 * @author eden
 * @date 2023/9/23 22:00:00
 */
public interface ActivityWhitelistRuleService extends FrameworkService<ActivityWhitelistRule, Long> {

    /**
     * spring bean 名称
     */
    String BEAN_NAME = "activityWhitelistRuleService";
}
