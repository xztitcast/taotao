package com.bovine.taotao.framework.i.service.extend;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * 活动组件响应实体类
 * @author eden
 * @date 2023/9/23 22:20:20
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityCompositeResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 响应code
     */
    private int code;

    /**
     * 消息体
     */
    private String message;

    /**
     * 数据
     */
    private Map<String, Object> data;
}
