package com.bovine.taotao.framework.entity;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 活动上下文实体类
 * @author eden
 * @date 2023/9/23 21:38:38
 */
@Getter
@Setter
@TableName(value = "tb_activity_context")
public class ActivityContext implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 活动ID
     */
    private Long id;

    /**
     * 步骤一(活动基础信息)
     */
    private String step0;

    /**
     * 步骤二(活动通用规则)
     */
    private String step1;

    /**
     * 步骤三(活动策略)
     */
    private String step2;

    /**
     * 步骤四(活动权益)
     */
    private String step3;

    /**
     * 步骤五(活动裂变)
     */
    private String step4;

    /**
     * 步骤六
     */
    private String step5;

    /**
     * 追加
     * @param step1
     */
    public void append(String step1) {
        JSONArray arr = null;
        if(this.step1 == null) {
            arr = new JSONArray(step1);
        }else {
            arr = JSON.parseArray(this.step1);
        }
        this.step1 = arr.toJSONString();
    }

    /**
     * 删除
     * @param step1
     * @return
     */
    public boolean remove(String step1) {
        if(this.step1 == null) {
            return false;
        }
        JSONArray arr = JSON.parseArray(this.step1);
        boolean remove = arr.remove(step1);
        if(remove) {
            this.step1 = arr.toJSONString();
        }
        return remove;
    }
}
