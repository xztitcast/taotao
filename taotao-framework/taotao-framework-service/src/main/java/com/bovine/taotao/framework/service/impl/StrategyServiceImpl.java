package com.bovine.taotao.framework.service.impl;

import com.bovine.taotao.common.core.BaseModel;
import com.bovine.taotao.common.core.P;
import com.bovine.taotao.framework.entity.Strategy;
import com.bovine.taotao.framework.mapper.StrategyMapper;
import com.bovine.taotao.framework.i.service.StrategyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * <p>
 * 玩法策略表 服务实现类
 * </p>
 *
 * @author Eden
 * @since 2023-09-29
 */
@Service
@DubboService(interfaceClass = StrategyService.class)
public class StrategyServiceImpl extends ServiceImpl<StrategyMapper, Strategy> implements StrategyService {

    @Override
    public P<Strategy> getBaseList(BaseModel m) {
        return StrategyService.super.getBaseList(m);
    }

    @Override
    public Strategy getEntity(String id) {
        return this.getById(id);
    }

    @Override
    @Transactional
    public Strategy saveEntity(Strategy t) {
        this.save(t);
        return t;
    }

    @Override
    @Transactional
    public boolean updateEntity(Strategy t) {
        return this.updateById(t);
    }

    @Override
    @Transactional
    public boolean delete(Collection<String> ids) {
        return this.removeBatchByIds(ids);
    }

    @Override
    @Transactional
    public boolean delete(String id) {
        return this.removeById(id);
    }
}
