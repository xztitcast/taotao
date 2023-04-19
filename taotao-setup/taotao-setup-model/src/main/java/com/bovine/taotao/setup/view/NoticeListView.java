package com.bovine.taotao.setup.view;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.bovine.taotao.common.core.ModelAndView;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * @description: 小程序前后端交互 公告通知数据实体类
 * @author: lujianxiong
 * @date: 2023/3/13 15:42
 */
@Getter
@Setter
public class NoticeListView extends ModelAndView<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 分类(1系统更新 2活动通知 3新闻资讯 4小程序首页公告)
     */
    private Integer classify;

    /**
     * 标题
     */
    private String title;

    /**
     * 公告内容
     */
    private String content;

    /**
     * 跳转链接
     */
    private String jumplink;

    /**
     * 分享主题
     */
    private String shareTitle;

    /**
     * 分享内容
     */
    private String shareContent;

    /**
     * 发布时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created;
}
