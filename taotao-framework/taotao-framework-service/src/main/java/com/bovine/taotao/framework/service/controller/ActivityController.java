package com.bovine.taotao.framework.service.controller;

import com.alibaba.fastjson2.JSON;
import com.bovine.taotao.common.core.Principal;
import com.bovine.taotao.common.core.R;
import com.bovine.taotao.common.core.S;
import com.bovine.taotao.common.security.annotation.Entity;
import com.bovine.taotao.common.security.annotation.Subject;
import com.bovine.taotao.framework.entity.Activity;
import com.bovine.taotao.framework.i.service.ActivityService;
import com.bovine.taotao.framework.model.ActivityJoinModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 活动控制器
 * @author eden
 * @date 2023/9/24 10:28:28
 */
@Slf4j
@RestController
@RequestMapping("/framework/activity")
public class ActivityController {

    @DubboReference
    private ActivityService activityService;

    /**
     * 参与活动
     * @param model 入参
     * @param principal 参与活动用户
     * @return
     */
    @PostMapping("/join")
    public R join(@RequestBody ActivityJoinModel model) {
        Activity entity = this.activityService.getEntity(model.getId());
        if(entity == null) {
            return R.error(S.ACTIVITY_NOTFOUND_ERROR);
        }
        /*
        * 结束时间提前一秒判断
        * 以免造成活动组合中本地缓存失效报错
        */
        /*Date date = new Date();
        if(date.before(entity.getStartTime()) || date.after(new Date(entity.getEndTime().getTime() - 1000))) {
            return R.error(S.ACTIVITY_NOT_STARTED_ERROR);
        }
        principal.setPname(model.getPname());
        principal.setCname(model.getCname());
        principal.setAreaname(model.getAreaname());
        log.info("参与活动入参: {}, 用户: {}", JSON.toJSONString(model), JSON.toJSONString(principal));*/
        //ActivityCompositeResponse response = this.activityService.doJoin(entity, principal);
        return R.ok();
    }
}
