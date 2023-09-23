package com.bovine.taotao.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bovine.taotao.common.core.BaseModel;
import com.bovine.taotao.common.core.P;
import com.bovine.taotao.framework.entity.ActivityContext;
import com.bovine.taotao.framework.entity.ActivityWhitelistRule;
import com.bovine.taotao.framework.i.service.ActivityContextService;
import com.bovine.taotao.framework.i.service.ActivityWhitelistRuleService;
import com.bovine.taotao.framework.mapper.ActivityWhitelistRuleMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;

/**
 * 活动白名单Service业务逻辑接口
 * @author eden
 * @date 2023/9/23 22:01:01
 */
@Service(ActivityWhitelistRuleService.BEAN_NAME)
@DubboService(interfaceClass = ActivityWhitelistRuleService.class)
public class ActivityWhitelistRuleServiceImpl extends ServiceImpl<ActivityWhitelistRuleMapper, ActivityWhitelistRule> implements IService<ActivityWhitelistRule>, ActivityWhitelistRuleService {

    @Autowired
    private ActivityContextService activityContextService;

    @Override
    public P<ActivityWhitelistRule> getBaseList(BaseModel m) {
        return ActivityWhitelistRuleService.super.getBaseList(m);
    }

    @Override
    public ActivityWhitelistRule getEntity(Long id) {
        return this.getById(id);
    }

    @Override
    @Transactional
    public ActivityWhitelistRule saveEntity(ActivityWhitelistRule t) {
        /*
        * 白名单与活动乃是多对一的关系
        * 后台可能一条条的新增
        * 所以添加的时候先判断上下文是否已经存在
        */
        boolean save = this.save(t);
        if(save) {
            ActivityContext context = this.activityContextService.getEntity(t.getId());
            if(!context.getStep1().contains(BEAN_NAME)) {
                context.append(BEAN_NAME);
                this.activityContextService.updateEntity(context);
            }
        }
        return t;
    }

    @Override
    @Transactional
    public boolean updateEntity(ActivityWhitelistRule t) {
        /*
        * 注意: 活动开始时尽量不要修改白名单信息, 一定修改请先暂停活动
        * 白名单与活动乃是多对一的关系
        * 后台修改操作可能一条条修改
        * 修改的条件有可能是延期有可能是失效
        * 所以要根据修改的条件判断是否删除上下文
        */
        boolean update = this.updateById(t);
        if(update) {
            LambdaQueryWrapper<ActivityWhitelistRule> queryWrapper = Wrappers.lambdaQuery(ActivityWhitelistRule.class).eq(ActivityWhitelistRule::getActivityId, t.getActivityId()).lt(ActivityWhitelistRule::getExpireAt, new Date());
            long count = this.count(queryWrapper);
            if(count <= 0) {
                ActivityContext context = this.activityContextService.getEntity(t.getId());
                context.remove(BEAN_NAME);
                this.activityContextService.updateEntity(context);
            }
        }
        return update;
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
