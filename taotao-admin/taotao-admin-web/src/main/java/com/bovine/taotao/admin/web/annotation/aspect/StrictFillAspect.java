package com.bovine.taotao.admin.web.annotation.aspect;

import com.bovine.taotao.admin.web.annotation.Fill;
import com.bovine.taotao.admin.web.annotation.FillType;
import com.bovine.taotao.admin.web.entity.SysUser;
import com.bovine.taotao.common.mybatis.entity.CreateEntity;
import com.bovine.taotao.common.mybatis.entity.TissueEntity;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 填充数据切面
 * @author eden
 * @date 2023年2月19日 下午4:30:33
 */
@Aspect
@Order(1)
@Component
public class StrictFillAspect {

	@Pointcut("@annotation(com.bovine.taotao.admin.web.annotation.Fill)")
	public void fillPointCut() { 
		
	}
	
	/**
	 * 环绕通知
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("fillPointCut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		Fill fill = method.getAnnotation(Fill.class);
		if(fill == null) {
			return joinPoint.proceed();
		}
		FillType value = fill.value();
		Object[] args = joinPoint.getArgs();
		SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		for(Object arg : args) {
			if(arg instanceof TissueEntity && value == FillType.INSERT) {
				setTissueEntityEnhance(arg, sysUser);
			}
			if(arg instanceof CreateEntity && value == FillType.INSERT) {
				setCreateEntityEnhance(arg, sysUser);
			}
			if(value == FillType.UPDATE) {
				setUpdateEntityEnhance(arg, sysUser);
			}
		}
		return joinPoint.proceed();
	}
	
	/**
	 * 填充机构数据
	 * @param arg
	 * @param sysUser
	 */
	private void setTissueEntityEnhance(Object arg, SysUser sysUser) {
		TissueEntity te = (TissueEntity)arg;
		te.setTisid(sysUser.getTisid());
		te.setTisname(sysUser.getTisname());
	}
	
	/**
	 * 填充创建者数据
	 * @param arg
	 * @param sysUser
	 */
	private void setCreateEntityEnhance(Object arg, SysUser sysUser) {
		CreateEntity ce = (CreateEntity)arg;
		ce.setCreator(sysUser.getId());
		ce.setCreateName(sysUser.getUsername());
		setUpdateEntityEnhance(arg, sysUser);
	}
	
	/**
	 * 填充更新者数据
	 * @param arg
	 * @param sysUser
	 */
	private void setUpdateEntityEnhance(Object arg, SysUser sysUser) {
		CreateEntity ce = (CreateEntity)arg;
		ce.setUpdateName(sysUser.getUsername());
		ce.setUpdater(sysUser.getId());
	}
}
