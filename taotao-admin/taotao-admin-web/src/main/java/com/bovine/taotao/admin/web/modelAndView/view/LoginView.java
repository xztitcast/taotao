package com.bovine.taotao.admin.web.modelAndView.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 返回后台系统登录数据
 * @author eden
 * @date 2024/1/14 22:26:26
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginView implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * token
     */
    private String token;

    /**
     * 失效时间
     */
    private Long expire;

    /**
     * 双因子验证
     */
    private boolean factor2;

}
