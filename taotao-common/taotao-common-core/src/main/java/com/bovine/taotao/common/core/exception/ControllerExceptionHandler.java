package com.bovine.taotao.common.core.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bovine.taotao.common.core.R;

import lombok.extern.slf4j.Slf4j;

import java.util.Set;

/**
 * 全局控制器异常捕获
 * @author eden
 * @date 2022年10月23日 上午9:59:11
 */
@Slf4j
public abstract class ControllerExceptionHandler {

	/**
	 * 验证类异常 controller层参数验证
	 * @param e
	 * @return
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	public R constraintViolationException (ConstraintViolationException e) {
		Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
		String message = null;
		for(ConstraintViolation<?> constraintViolation : constraintViolations) {
			message = constraintViolation.getMessage();
			break;
		}
		log.error("【**********验证信息异常:{}**********】", message);
		return R.error(message);
	}

	/**
	 * 验证类异常 实体类里面属性验证
	 * 注: @Valid @Validate 必须加在方法上才会有效，加在类上无法捕捉该异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public R methodArgumentNotValidException (MethodArgumentNotValidException e) {

		BindingResult bindingResult = e.getBindingResult();

		String message = null;

		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			message = fieldError.getDefaultMessage();
			break;
		}
		log.error("【**********验证信息异常:{}**********】", message);
		return R.error(message);
	}
	
	/**
	 * 验证类异常 实体类里面属性验证
	 * 注: BindException 与 MethodArgumentNotValidException 都是捕捉实体类定义的验证
	 * MethodArgumentNotValidException 捕捉的JSON数据
	 * BindException 捕捉的是表单提交
	 * @param e
	 * @return
	 */
	@ExceptionHandler(BindException.class)
	public R bindException(BindException e) {
		BindingResult bindingResult = e.getBindingResult();
		String message = null;
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			message = fieldError.getDefaultMessage();
			break;
		}
		log.error("【**********验证信息异常:{}**********】", message);
		return R.error(message);
	}

	/**
	 * 请求缺少参数异常捕获
	 * @param e
	 * @return
	 */
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public R missingServletRequestParameterException (MissingServletRequestParameterException e) {

		log.error("【***********请求缺少参数：参数类型-{}，参数名-{}***********】", e.getParameterType(), e.getParameterName());

		String message = "缺少请求参数【参数类型:" + e.getParameterType() + ",参数名:" + e.getParameterName() + "】";

		return R.error(message);
	}

	/**
	 * 系统异常
	 * @param e
	 * @return
	 */
    @ExceptionHandler(Exception.class)
    public R exception(Exception e){
    	log.error("系统异常",e);
        return R.error();
    }

}
