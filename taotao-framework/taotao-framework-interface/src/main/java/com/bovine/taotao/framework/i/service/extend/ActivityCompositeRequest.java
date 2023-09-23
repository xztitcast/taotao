package com.bovine.taotao.framework.i.service.extend;

import com.bovine.taotao.common.core.Principal;
import com.bovine.taotao.framework.entity.Activity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * 活动组合请求实体类
 * @author eden
 * @date 2023/9/23 22:15:15
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityCompositeRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 信号灯 组合索引位置
     */
    private int signal;

    /**
     * 参数活动
     */
    private Activity activity;

    /**
     * 参与用户
     */
    private Principal principal;

    /**
     * 请求扩展属性
     */
    private Map<String, Object> attribute;
}
