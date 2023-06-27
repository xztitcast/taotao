package com.bovine.taotao.admin.web.annotation.aspect;

import com.alibaba.fastjson2.JSON;
import com.bovine.taotao.admin.web.annotation.Log;
import com.bovine.taotao.admin.web.entity.SysLog;
import com.bovine.taotao.admin.web.entity.SysUser;
import com.bovine.taotao.admin.web.service.SysLogService;
import com.bovine.taotao.admin.web.service.impl.UserDetailsServiceImpl.LoginUserDetails;
import com.bovine.taotao.common.core.utils.IPUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Date;


/**
 * 系统日志，切面处理类
 */
@Aspect
@Order
@Component
public class SysLogAspect {
	
	@Autowired
	private SysLogService sysLogService;
	
	@Pointcut("@annotation(com.bovine.taotao.admin.web.annotation.Log)")
	public void logPointCut() { 
		
	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		//执行方法
		Object result = point.proceed();
		//执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;

		//保存日志
		saveSysLog(point, time);

		return result;
	}

	private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		
		SysLog sysLog = new SysLog();
		Log log = method.getAnnotation(Log.class);
		if(log != null) sysLog.setOperation(log.value());

		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		sysLog.setMethod(className + "." + methodName + "()");
		
		String parmas = JSON.toJSONString(joinPoint.getArgs());
		sysLog.setParams(parmas);

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

		sysLog.setIp(IPUtil.getIpAddr(request));
		LoginUserDetails principal = (LoginUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = principal.getUsername();
		sysLog.setUsername(username);
		sysLog.setTime(time);
		sysLog.setCreated(new Date());

		sysLogService.save(sysLog);
	}
}
