package com.bovine.taotao.admin.web.service.impl;

import java.util.Date;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bovine.taotao.admin.web.entity.SysLog;
import com.bovine.taotao.admin.web.event.SysLoginEvent;
import com.bovine.taotao.admin.web.mapper.SysLogMapper;
import com.bovine.taotao.admin.web.model.LoginModel;
import com.bovine.taotao.admin.web.model.UserModel;
import com.bovine.taotao.admin.web.service.SysLogService;
import com.bovine.taotao.common.core.P;
import com.bovine.taotao.common.core.utils.IPUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService, ApplicationListener<SysLoginEvent> {

	@Override
	public P<SysLog> getSysLogList(UserModel um) {
		IPage<SysLog> page = new Page<>(um.getPageNum(), um.getPageSize());
		QueryWrapper<SysLog> query = new QueryWrapper<>();
		query.eq(StringUtils.isNotBlank(um.getUsername()), "`username`", um.getUsername()).orderBy(true, um.getOrder(), um.getOrderField());
		page(page, query);
		return new P<>(page.getTotal(), page.getRecords());
	}

	@Async
	@Override
	public void onApplicationEvent(SysLoginEvent event) {
		SysLog sysLog = new SysLog();
		Object source = event.getSource();
		if(source instanceof LoginModel) {
			LoginModel form = (LoginModel)source;
			sysLog.setOperation("登录");
			sysLog.setUsername(form.getUsername());
			sysLog.setMethod("com.jc.smart.admin.web.controller.SysLoginController.login()");
		}else {
			sysLog.setOperation("退出登录");
			sysLog.setUsername(event.getSource().toString());
			sysLog.setMethod("com.jc.smart.admin.web.controller.SysLoginController.logout()");
		}
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		sysLog.setIp(IPUtil.getIpAddr(request));
		sysLog.setTime(0L);
		sysLog.setCreated(new Date());
		this.save(sysLog);
	}

}
