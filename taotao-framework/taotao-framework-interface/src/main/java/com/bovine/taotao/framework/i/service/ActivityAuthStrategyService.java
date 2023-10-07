package com.bovine.taotao.framework.i.service;

import com.bovine.taotao.framework.entity.ActivityAuthStrategy;
import com.bovine.taotao.common.core.injecter.FrameworkService;

/**
 * <p>
 * 支付认证策略表 服务类
 * </p>
 *
 * @author Eden
 * @since 2023-09-29
 */
public interface ActivityAuthStrategyService extends FrameworkService<ActivityAuthStrategy, Long> {

    /**
     * Spring bean 名称
     */
    String BEAN_NAME = "activityAuthStrategyServiceImpl";

}
