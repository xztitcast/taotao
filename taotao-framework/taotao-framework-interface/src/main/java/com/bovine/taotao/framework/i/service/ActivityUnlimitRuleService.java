package com.bovine.taotao.framework.i.service;

import com.bovine.taotao.common.core.injecter.FrameworkService;
import com.bovine.taotao.framework.entity.ActivityUnlimitRule;

/**
 * 活动次数Service业务逻辑接口
 * @author eden
 * @date 2023/9/23 22:06:06
 */
public interface ActivityUnlimitRuleService extends FrameworkService<ActivityUnlimitRule, Long> {

    String BEAN_NAME = "activityUnlimitRuleService";
}
