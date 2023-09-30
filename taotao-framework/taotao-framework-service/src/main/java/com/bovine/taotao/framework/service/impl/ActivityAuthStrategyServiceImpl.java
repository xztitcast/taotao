package com.bovine.taotao.framework.service.impl;

import com.bovine.taotao.common.core.BaseModel;
import com.bovine.taotao.common.core.P;
import com.bovine.taotao.framework.entity.ActivityAuthStrategy;
import com.bovine.taotao.framework.mapper.ActivityAuthStrategyMapper;
import com.bovine.taotao.framework.i.service.ActivityAuthStrategyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * <p>
 * 支付认证策略表 服务实现类
 * </p>
 *
 * @author Eden
 * @since 2023-09-29
 */
@Service
@DubboService(interfaceClass = ActivityAuthStrategyService.class)
public class ActivityAuthStrategyServiceImpl extends ServiceImpl<ActivityAuthStrategyMapper, ActivityAuthStrategy> implements ActivityAuthStrategyService {
    @Override
    public P<ActivityAuthStrategy> getBaseList(BaseModel m) {
        return ActivityAuthStrategyService.super.getBaseList(m);
    }

    @Override
    public ActivityAuthStrategy getEntity(Long id) {
        return this.getById(id);
    }

    @Override
    @Transactional
    public ActivityAuthStrategy saveEntity(ActivityAuthStrategy t) {
        this.save(t);
        return t;
    }

    @Override
    @Transactional
    public boolean updateEntity(ActivityAuthStrategy t) {
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
