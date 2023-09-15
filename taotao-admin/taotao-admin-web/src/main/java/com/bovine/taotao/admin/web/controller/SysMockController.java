package com.bovine.taotao.admin.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bovine.taotao.admin.web.entity.SysUser;
import com.bovine.taotao.admin.web.modelAndView.model.UserModel;
import com.bovine.taotao.admin.web.service.SysUserService;
import com.bovine.taotao.common.core.BaseModel;
import com.bovine.taotao.common.core.P;
import com.bovine.taotao.common.core.R;
import com.bovine.taotao.common.core.utils.AESUtil;

@RestController
@RequestMapping("/test/mock")
public class SysMockController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/create")
    public R create(UserModel form){
        P<SysUser> list = sysUserService.getSysUserList(form, 1L);
        return R.ok(list);
    }

    @GetMapping("/demo1")
    public R demo1(BaseModel vo){
        return R.ok(vo);
    }
    
    @GetMapping("/demo2")
    public R demo2() {
    	String encrypt = AESUtil.encrypt("123456");
    	return R.ok(encrypt);
    }
}
