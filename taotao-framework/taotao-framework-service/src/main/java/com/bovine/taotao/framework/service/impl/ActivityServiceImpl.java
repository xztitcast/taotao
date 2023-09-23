package com.bovine.taotao.framework.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bovine.taotao.common.core.BaseModel;
import com.bovine.taotao.common.core.P;
import com.bovine.taotao.framework.entity.Activity;
import com.bovine.taotao.framework.entity.ActivityContext;
import com.bovine.taotao.framework.i.service.ActivityContextService;
import com.bovine.taotao.framework.i.service.ActivityService;
import com.bovine.taotao.framework.mapper.ActivityMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * 活动Service业务逻辑实现类
 * @author eden
 * @date 2023/9/23 16:43:43
 */
@Service(ActivityService.BEAN_NAME)
@DubboService(interfaceClass = ActivityService.class)
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements IService<Activity>, ActivityService {

    @Autowired
    private ActivityContextService activityContextService;

    @Override
    public P<Activity> getBaseList(BaseModel m) {
        return ActivityService.super.getBaseList(m);
    }

    @Override
    public Activity getEntity(Long id) {
        return this.getById(id);
    }

    @Override
    @Transactional
    public Activity saveEntity(Activity t) {
        boolean save = this.save(t);
        if(save) {
            ActivityContext context = new ActivityContext();
            context.setId(t.getId());
            context.setStep0(BEAN_NAME);
            this.activityContextService.saveEntity(context);
        }
        return t;
    }

    @Override
    @Transactional
    public boolean updateEntity(Activity t) {
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
