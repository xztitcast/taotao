package com.bovine.taotao.admin.web.global;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bovine.taotao.common.core.R;
import com.bovine.taotao.common.core.exception.ControllerExceptionHandler;
import com.bovine.taotao.common.core.exception.custom.SysServiceException;

import lombok.extern.slf4j.Slf4j;

/**
 * 系统模块全局异常捕获
 * @author eden
 * @date 2022年10月23日 上午10:13:34
 */
@Slf4j
@RestControllerAdvice
public class SysControllerExceptionHandler extends ControllerExceptionHandler {
	
	/**
	 * 系统业务异常捕获
	 * @param e
	 * @return
	 */
	@ExceptionHandler(SysServiceException.class)
	public R sysServiceException(SysServiceException e) {
		log.error("SysServiceException 业务异常", e);
		return R.error(e.getMessage());
	}
}
