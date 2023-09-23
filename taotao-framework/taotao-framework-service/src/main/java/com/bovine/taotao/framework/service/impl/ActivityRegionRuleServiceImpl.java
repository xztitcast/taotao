package com.bovine.taotao.framework.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bovine.taotao.common.core.BaseModel;
import com.bovine.taotao.common.core.P;
import com.bovine.taotao.framework.entity.ActivityContext;
import com.bovine.taotao.framework.entity.ActivityRegionRule;
import com.bovine.taotao.framework.i.service.ActivityContextService;
import com.bovine.taotao.framework.i.service.ActivityRegionRuleService;
import com.bovine.taotao.framework.mapper.ActivityRegionRuleMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

/**
 * 活动区域规则Service业务逻辑实现类
 * @author eden
 * @date 2023/9/23 21:55:55
 */
@Service(ActivityRegionRuleService.BEAN_NAME)
@DubboService(interfaceClass = ActivityRegionRuleService.class)
public class ActivityRegionRuleServiceImpl extends ServiceImpl<ActivityRegionRuleMapper, ActivityRegionRule> implements IService<ActivityRegionRule>, ActivityRegionRuleService {

    @Autowired
    private ActivityContextService activityContextService;

    @Override
    public P<ActivityRegionRule> getBaseList(BaseModel m) {
        return ActivityRegionRuleService.super.getBaseList(m);
    }

    @Override
    public ActivityRegionRule getEntity(Long id) {
        return this.getById(id);
    }

    @Override
    @Transactional
    public ActivityRegionRule saveEntity(ActivityRegionRule t) {
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
    public boolean updateEntity(ActivityRegionRule t) {
        if(CollectionUtils.isEmpty(t.getRegionList())) {
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
