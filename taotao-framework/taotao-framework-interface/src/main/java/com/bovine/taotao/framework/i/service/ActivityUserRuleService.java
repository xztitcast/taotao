package com.bovine.taotao.framework.i.service;

import com.bovine.taotao.common.core.injecter.FrameworkService;
import com.bovine.taotao.framework.entity.ActivityUserRule;

/**
 * 活动新客规则Service业务逻辑接口
 * @author eden
 * @date 2023/9/23 21:50:50
 */
public interface ActivityUserRuleService extends FrameworkService<ActivityUserRule, Long> {

    String BEAN_NAME = "activityUserRuleService";
}
