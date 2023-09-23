package com.bovine.taotao.framework.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bovine.taotao.common.mybatis.entity.CreateEntity;
import com.bovine.taotao.common.mybatis.handler.MybatisPlusList2JsonHandler;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 活动实体类
 * @author eden
 * @date 2023/9/23 16:37:37
 */
@Getter
@Setter
@TableName(value = "tb_activity")
public class Activity extends CreateEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 活动名称
     */
    private String name;

    /**
     * 活动开始时间
     */
    private Date startTime;

    /**
     * 活动结束时间
     */
    private Date endTime;

    /**
     * 展示开始时间
     */
    private Date showStartTime;

    /**
     * 展示结束时间
     */
    private Date showEndTime;

    /**
     * 指定时间段(周一/周天)
     */
    @TableField(typeHandler = MybatisPlusList2JsonHandler.class)
    private List<String> week;

    /**
     * 指定参与时间(00:00:00 - 23:59:59)
     */
    private String fixedTime;

    /**
     * 是否展示在前端(0:否 1:是)
     */
    private Integer showStatus;

    /**
     * 活动状态 1:暂停 2:启动
     */
    private Integer status;

    /**
     * 是否加入活动中心(0:否 1:是)
     */
    private Integer joinCenter;

    /**
     * 关联业务类型
     */
    private Integer businessType;

    /**
     * 活动背景图
     */
    private String backgroundImage;

    /**
     * 活动背景颜色
     */
    private String backgroundColor;

    /**
     * 活动跳转链接
     */
    private String linkUrl;

}
