package com.bovine.taotao.framework.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bovine.taotao.common.core.*;
import com.bovine.taotao.common.core.enums.BaseEnum;
import com.bovine.taotao.common.redis.kit.RedisLockHelper;
import com.bovine.taotao.framework.entity.Activity;
import com.bovine.taotao.framework.entity.ActivityContext;
import com.bovine.taotao.framework.entity.ActivityUnlimitRule;
import com.bovine.taotao.framework.i.service.ActivityContextService;
import com.bovine.taotao.framework.i.service.extend.ActivityComposite;
import com.bovine.taotao.framework.i.service.extend.ActivityCompositeRequest;
import com.bovine.taotao.framework.i.service.extend.ActivityCompositeResponse;
import com.bovine.taotao.framework.mapper.ActivityContextMapper;
import com.bovine.taotao.framework.service.extend.ActivityContextLocalCache;
import com.bovine.taotao.framework.service.extend.ActivityContextLocalCacheAdapter;
import com.bovine.taotao.framework.service.extend.ActivityManagerComposite;
import com.bovine.taotao.framework.service.extend.ActivityRedisMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.bovine.taotao.common.core.Constant.DELIMITER_COLON;

/**
 * 活动上下文Service业务逻辑实现类
 * @author eden
 * @date 2023/9/23 21:43:43
 */
@Slf4j
@Service("activityContextService")
@DubboService(interfaceClass = ActivityContextService.class)
public class ActivityContextServiceImpl extends ServiceImpl<ActivityContextMapper, ActivityContext> implements IService<ActivityContext>, ActivityContextService, ActivityRedisMessageListener, InitializingBean {

    private static final String ACTIVITY_KEY = "JC:CORE:ACTIVITY:";

    private static final String ACTIVITY_KEY_ID = ACTIVITY_KEY.concat("ID:");

    private static final String[] ACTIVITY_UNLIMIT = new String[]{Constant.TOTAL, Constant.MONTH, Constant.WEEK, Constant.DAY};

    private static final String[] ACTIVITY_UNLIMIT_MESSAGE = new String[]{"超过总次数限制", "超过每月次数限制", "超过每周次数限制", "超过每天次数限制"};

    /**
     * 活动上下文本地缓存
     * 本地缓存过期时间乃是redis ttl命令获取的时间
     * 由于客户端ttl命令有开销一来一回会存在毫秒级别的误差
     */
    private final ActivityContextLocalCache<String, ActivityComposite> activityContextLocalCache = new ActivityContextLocalCacheAdapter<>();

    @Autowired
    private RedisLockHelper redisLockHelper;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public P<ActivityContext> getBaseList(BaseModel m) {
        return ActivityContextService.super.getBaseList(m);
    }

    @Override
    public ActivityContext getEntity(Long id) {
        return this.getById(id);
    }

    @Override
    @Transactional
    public ActivityContext saveEntity(ActivityContext t) {
        this.save(t);
        return t;
    }

    @Override
    @Transactional
    public boolean updateEntity(ActivityContext t) {
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
    public boolean wramup(Long activityId, Date activityEndTime) {
        //预热前可以做些其他操作
        ActivityContext entity = this.getById(activityId);
        BoundValueOperations<String, String> boundValueOps = this.redisTemplate.boundValueOps(ACTIVITY_KEY_ID + activityId);
        boundValueOps.expireAt(activityEndTime);
        boundValueOps.set(JSON.toJSONString(entity));

        //发送redis消息
        this.redisTemplate.convertAndSend(ACTIVITY_CHANNEL.replace(CHANNEL_SUFFIX, activityId.toString()), ACTIVITY_KEY_ID + activityId);
        return true;
    }

    @Override
    public ActivityCompositeResponse doJoin(Activity activity, Principal principal) {
        final ActivityCompositeRequest request = new ActivityCompositeRequest(0, activity, principal, null);
        final ActivityCompositeResponse response = new ActivityCompositeResponse();
        ActivityComposite activityComposite = this.activityContextLocalCache.getIfPresent(ACTIVITY_KEY_ID + activity.getId());
        this.redisLockHelper.tryLock(this.getActivityKey(activity.getId(), principal.getId()), () -> {
            return activityComposite.doProcess(request, response);
        });
        return response;
    }

    @Override
    public boolean delegateToCompute(ActivityCompositeRequest request, ActivityCompositeResponse response) {
        Activity activity = request.getActivity();
        Principal principal = request.getPrincipal();
        ActivityUnlimitRule rule = (ActivityUnlimitRule)request.getAttribute().get("unlimit");
        for(int i = 0; i < ACTIVITY_UNLIMIT.length; i++) {
            Integer unlimit = rule.getUnlimit(ACTIVITY_UNLIMIT[i]);
            if(unlimit == null || unlimit <= 0) continue;
            String unlimitKey = this.getActivityKey(activity.getId(), principal.getId()).concat(DELIMITER_COLON).concat(ACTIVITY_UNLIMIT[i]);
            String value = this.redisTemplate.opsForValue().get(unlimitKey);
            if(StringUtils.isNotBlank(value)) {
                int number = Integer.parseInt(value);
                if(number >= unlimit) {
                    response.setCode(S.ERROR.getValue());
                    response.setMessage(ACTIVITY_UNLIMIT_MESSAGE[i]);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean delegateToCompute(ActivityCompositeRequest request) {
        Activity activity = request.getActivity();
        Principal principal = request.getPrincipal();
        ActivityUnlimitRule rule = (ActivityUnlimitRule)request.getAttribute().get("unlimit");
        for(String unlimit : ACTIVITY_UNLIMIT) {
            Integer limit = rule.getUnlimit(unlimit);
            if(limit == null || limit <= 0) continue;
            Date date = activity.getEndTime();
            if(Constant.MONTH.equals(unlimit)) {
                date = DateUtil.endOfMonth(new Date());
            }
            if(Constant.WEEK.equals(unlimit)) {
                date = DateUtil.endOfWeek(new Date());
            }
            if(Constant.DAY.equals(unlimit)) {
                date = DateUtil.endOfDay(new Date());
            }
            String unlimitKey = this.getActivityKey(activity.getId(), principal.getId()).concat(DELIMITER_COLON).concat(unlimit);
            BoundValueOperations<String, String> boundValueOps = this.redisTemplate.boundValueOps(unlimitKey);
            boundValueOps.expireAt(date);
            boundValueOps.increment();
        }
        return true;
    }

    @Override
    public String getActivityKey(Long activityId, Long userId) {
        return (ACTIVITY_KEY + activityId).concat(DELIMITER_COLON + userId);
    }

    @Override
    public void onMessage(String message) {
        String value = this.redisTemplate.opsForValue().get(message);
        ActivityContext entity = JSON.parseObject(value, ActivityContext.class);
        ActivityComposite activityComposite = new ActivityManagerComposite(entity.getId().toString(), BaseEnum.ONE);

        ActivityComposite baseActivityComposite = new ActivityManagerComposite(entity.getStep0(), BaseEnum.TWO);
        ActivityComposite baseActivity = this.applicationContext.getBean(entity.getStep0(), ActivityComposite.class);
        baseActivityComposite.add(baseActivity);

        ActivityComposite activityRuleComposite = new ActivityManagerComposite(entity.getStep1(), BaseEnum.TWO);
        List<String> ruleList = JSON.parseArray(entity.getStep1(), String.class);
        ruleList.forEach(beanName -> activityRuleComposite.add(this.applicationContext.getBean(entity.getStep1(), ActivityComposite.class)));

        ActivityComposite activityProcessComposite = new ActivityManagerComposite(entity.getStep2(), BaseEnum.TWO);
        ActivityComposite activityProcess = this.applicationContext.getBean(entity.getStep2(), ActivityComposite.class);
        activityProcessComposite.add(activityProcess);

        ActivityComposite activityShareComposite = new ActivityManagerComposite(entity.getStep3(), BaseEnum.TWO);
        ActivityComposite activityShare = this.applicationContext.getBean(entity.getStep3(), ActivityComposite.class);
        activityShareComposite.add(activityShare);

        activityComposite.add(baseActivityComposite);
        activityComposite.add(activityRuleComposite);
        activityComposite.add(activityProcessComposite);
        activityComposite.add(activityShareComposite);

        this.activityContextLocalCache.put(message, activityComposite, this.redisTemplate.getExpire(message));
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("活动监听绑定: {}", new String(pattern));

        String body = new String(message.getBody());

        log.info("活动监听内容: {}", new String(pattern));

        onMessage(body);

        log.info("活动解析内容成功");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Set<String> keys = this.redisTemplate.keys(ACTIVITY_KEY);
        keys.forEach(this::onMessage);
    }
}
