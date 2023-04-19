package com.bovine.taotao.setup.view;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @description:
 * @author: lujianxiong
 * @date: 2023/3/13 18:39
 */
@Getter
@Setter
public class NoticeView implements Serializable {

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
}
