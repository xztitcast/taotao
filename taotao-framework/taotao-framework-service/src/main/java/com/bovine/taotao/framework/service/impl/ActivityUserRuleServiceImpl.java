package com.bovine.taotao.framework.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bovine.taotao.common.core.BaseModel;
import com.bovine.taotao.common.core.P;
import com.bovine.taotao.framework.entity.ActivityContext;
import com.bovine.taotao.framework.entity.ActivityUserRule;
import com.bovine.taotao.framework.i.service.ActivityContextService;
import com.bovine.taotao.framework.i.service.ActivityUserRuleService;
import com.bovine.taotao.framework.mapper.ActivityUserRuleMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * 活动新客限制Service业务逻辑实现类
 * @author eden
 * @date 2023/9/23 21:51:51
 */
@Service(ActivityUserRuleService.BEAN_NAME)
@DubboService(interfaceClass = ActivityUserRuleService.class)
public class ActivityUserRuleServiceImpl extends ServiceImpl<ActivityUserRuleMapper, ActivityUserRule> implements IService<ActivityUserRule>, ActivityUserRuleService {

    @Autowired
    private ActivityContextService activityContextService;

    @Override
    public P<ActivityUserRule> getBaseList(BaseModel m) {
        return ActivityUserRuleService.super.getBaseList(m);
    }

    @Override
    public ActivityUserRule getEntity(Long id) {
        return this.getEntity(id);
    }

    @Override
    @Transactional
    public ActivityUserRule saveEntity(ActivityUserRule t) {
        boolean save = this.save(t);
        if(save) {
            ActivityContext context = this.activityContextService.getEntity(t.getId());
            context.append(BEAN_NAME);
            this.activityContextService.updateEntity(context);
        }
        return t;
    }

    @Override
    @Transactional
    public boolean updateEntity(ActivityUserRule t) {
        if(t.getRegisterTime() == null) {
            ActivityContext context = this.activityContextService.getEntity(t.getId());
            context.remove(BEAN_NAME);
            this.activityContextService.updateEntity(context);
        }
        return this.updateById(t);
    }

    @Override
    @Transactional
    public boolean delete(Collection<Long> ids) {
        return this.removeBatchByIds(ids);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        return this.removeById(id);
    }
}
