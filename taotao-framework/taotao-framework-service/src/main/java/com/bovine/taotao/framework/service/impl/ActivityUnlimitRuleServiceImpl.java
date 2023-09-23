package com.bovine.taotao.framework.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bovine.taotao.common.core.BaseModel;
import com.bovine.taotao.common.core.P;
import com.bovine.taotao.framework.entity.ActivityContext;
import com.bovine.taotao.framework.entity.ActivityUnlimitRule;
import com.bovine.taotao.framework.i.service.ActivityContextService;
import com.bovine.taotao.framework.i.service.ActivityUnlimitRuleService;
import com.bovine.taotao.framework.mapper.ActivityUnlimitRuleMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * @author eden
 * @date 2023/9/23 22:06:06
 */
@Service(ActivityUnlimitRuleService.BEAN_NAME)
@DubboService(interfaceClass = ActivityUnlimitRuleService.class)
public class ActivityUnlimitRuleServiceImpl extends ServiceImpl<ActivityUnlimitRuleMapper, ActivityUnlimitRule> implements IService< ActivityUnlimitRule>, ActivityUnlimitRuleService {

    @Autowired
    private ActivityContextService activityContextService;

    @Override
    public P<ActivityUnlimitRule> getBaseList(BaseModel m) {
        return ActivityUnlimitRuleService.super.getBaseList(m);
    }

    @Override
    public ActivityUnlimitRule getEntity(Long id) {
        return this.getById(id);
    }

    @Override
    @Transactional
    public ActivityUnlimitRule saveEntity(ActivityUnlimitRule t) {
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
    public boolean updateEntity(ActivityUnlimitRule t) {
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
