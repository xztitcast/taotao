package com.bovine.taotao.framework.service.extend;

import org.springframework.data.redis.connection.MessageListener;

/**
 * 活动Redis消息监听器
 * @author eden
 * @date 2023/9/24 0:05:05
 */
public interface ActivityRedisMessageListener extends MessageListener {

    /**
     * 通配符
     */
    String CHANNEL_SUFFIX = "*";

    /**
     * 通道
     */
    String ACTIVITY_CHANNEL = "activity.".concat(CHANNEL_SUFFIX);

    /**
     * 解析消息(重载)
     * @param message
     */
    void onMessage(String message);

}
