package com.bovine.taotao.framework.i.service;

import com.bovine.taotao.common.core.Principal;
import com.bovine.taotao.framework.entity.Activity;
import com.bovine.taotao.framework.i.service.extend.ActivityCompositeResponse;

/**
 * 参加活动
 * @author eden
 * @date 2023/9/24 11:27:27
 */
public interface AbstractActivityService {

    /**
     * 去参加活动
     * @param activity 参加的活动
     * @param principal 参加活动主体
     * @return ActivityCompositeResponse
     */
    ActivityCompositeResponse doJoin(Activity activity, Principal principal);
}
