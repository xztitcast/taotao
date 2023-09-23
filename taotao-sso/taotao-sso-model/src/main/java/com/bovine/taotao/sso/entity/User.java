package com.bovine.taotao.sso.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户实体类
 * 与common包中 Principal类属性一一相同
 */
@Getter
@Setter
@TableName(value = "tb_user")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 机构ID
     */
    private Long tisid;

    /**
     * 用户名(即手机号)
     */
    private String mobile;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 微信openid
     */
    private String openid;

    /**
     * 微信unionid
     */
    private String unionid;

    /**
     * 用户标识ID
     */
    private String userpasid;

    /**
     * 用户类型(1:真实用户 2:虚拟用户)
     */
    private Integer type;

    /**
     * 省份
     */
    private String pname;

    /**
     * 城市
     */
    private String cname;

    /**
     * 地区
     */
    private String areaname;

    /**
     * 省ID
     */
    private String pid;

    /**
     * 城市ID
     */
    private String cid;

    /**
     * 地区ID
     */
    private String areaId;

    /**
     * 国家
     */
    private String country;

    /**
     * 头像
     */
    private String avatar;
}
