package com.bovine.taotao.framework.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bovine.taotao.common.core.BaseModel;
import com.bovine.taotao.common.core.Constant;
import com.bovine.taotao.common.core.P;
import com.bovine.taotao.common.core.Principal;
import com.bovine.taotao.common.core.enums.BaseEnum;
import com.bovine.taotao.framework.entity.Activity;
import com.bovine.taotao.framework.entity.ActivityContext;
import com.bovine.taotao.framework.i.service.ActivityContextService;
import com.bovine.taotao.framework.i.service.ActivityService;
import com.bovine.taotao.framework.i.service.extend.ActivityComposite;
import com.bovine.taotao.framework.i.service.extend.ActivityCompositeRequest;
import com.bovine.taotao.framework.i.service.extend.ActivityCompositeResponse;
import com.bovine.taotao.framework.mapper.ActivityMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;

/**
 * 活动Service业务逻辑实现类
 * @author eden
 * @date 2023/9/23 16:43:43
 */
@Service(ActivityService.BEAN_NAME)
@DubboService(interfaceClass = ActivityService.class)
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements IService<Activity>, ActivityService, ActivityComposite {

    @Autowired
    private ActivityContextService activityContextService;

    @Override
    public ActivityCompositeResponse doJoin(Activity activity, Principal principal) {
        return this.activityContextService.doJoin(activity, principal);
    }

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

    @Override
    public boolean doProcess(ActivityCompositeRequest request, ActivityCompositeResponse response) {
        Activity entity = request.getActivity();
        Date date = new Date();
        if(date.before(entity.getStartTime()) || date.after(entity.getEndTime())) {
            response.setCode(-1);
            response.setMessage("活动还未开始或已结束!");
            return false;
        }
        int dayOfWeek = DateUtil.dayOfWeek(date);
        if(!entity.getWeek().contains(dayOfWeek+"")) {
            response.setCode(-1);
            response.setMessage("活动还未开始或已结束!");
            return false;
        }
        if(entity.getStatus() == BaseEnum.ONE) {
            response.setCode(-1);
            response.setMessage("活动已暂停!");
            return false;
        }
        String fixedTime = entity.getFixedTime();
        String[] split = fixedTime.split(Constant.DELIMITER_ROT);
        DateTime start = DateUtil.parseTimeToday(split[0]);
        DateTime end = DateUtil.parseTimeToday(split[1]);
        if(date.before(start) || date.after(end)) {
            response.setCode(-1);
            response.setMessage("活动还未开始或已结束");
            return false;
        }
        return true;
    }

}
