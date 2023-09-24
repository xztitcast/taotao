package com.bovine.taotao.framework.i.service;

import com.bovine.taotao.common.core.injecter.FrameworkService;
import com.bovine.taotao.framework.entity.ActivityContext;
import com.bovine.taotao.framework.i.service.extend.ActivityCompositeRequest;
import com.bovine.taotao.framework.i.service.extend.ActivityCompositeResponse;

import java.util.Date;

/**
 * 活动上下文Service业务逻辑接口
 * @author eden
 * @date 2023/9/23 21:42:42
 */
public interface ActivityContextService extends AbstractActivityService, FrameworkService<ActivityContext, Long> {

    /**
     * 活动预热
     * @param activityId 活动ID
     * @param activityEndTime 活动结束时间
     * @return
     */
    boolean wramup(Long activityId, Date activityEndTime);

    /**
     * 委托计算
     * 计算用户参与活动的次数限制
     * 根据条件限制判断用户不同维度否超过限制
     * @param request
     * @param response
     * @return
     */
    boolean delegateToCompute(ActivityCompositeRequest request, ActivityCompositeResponse response);

    /**
     * 重载委托计算
     * 用户每次参与完活动后将不同维度的参与次数条件+1
     * @param request
     * @return
     */
    boolean delegateToCompute(ActivityCompositeRequest request);

    /**
     * 获取活动ID
     * @param activityId
     * @param userId
     * @return
     */
    String getActivityKey(Long activityId, Long userId);
}
