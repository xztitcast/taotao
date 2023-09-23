package com.bovine.taotao.framework.i.service;

import com.bovine.taotao.common.core.injecter.FrameworkService;
import com.bovine.taotao.framework.entity.Activity;

/**
 * 活动Service业务逻辑接口
 * @author eden
 * @date 2023/9/23 16:42:42
 */
public interface ActivityService extends FrameworkService<Activity, Long> {

    /**
     * Spring bean 名称
     */
    String BEAN_NAME = "activityService";
}
