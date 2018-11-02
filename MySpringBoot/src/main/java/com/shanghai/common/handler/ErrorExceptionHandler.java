package com.shanghai.common.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.shanghai.common.utils.exception.GlobalException;
import com.shanghai.common.utils.exception.LoginException;

/**
 * @author YeJR
 * @version: 2018年5月28日 上午10:07:51
 * 异常处理类
 * 可自定义拦截不同的异常进行不同操作
 * @ControllerAdvice 切面
 */
@ControllerAdvice
public class ErrorExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 拦截登录异常
	 * @param exception
	 * @return
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler({ LoginException.class })
	public ModelAndView handlerLoginException(LoginException exception) {
		logger.error("拦截登录异常:{}", exception);
		ModelAndView model = new ModelAndView();
		model.addObject("msg", exception.getCodeMsg().getMsg());
		model.setViewName("modules/login");
		return model;
	}

	/**
	 * 拦截全局异常
	 * @param exception
	 * @return
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler({ GlobalException.class })
	public ModelAndView handlerGlobalException(GlobalException exception) {
		logger.error("拦截全局异常:{}", exception);
		ModelAndView model = new ModelAndView();
		model.addObject("exception", exception);
		model.setViewName("error/500");
		return model;
	}
	
	/**
	 * 统一异常处理
	 * @param exception
	 * @return
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler({ Exception.class })
	public ModelAndView handlerException(Exception exception) {
		logger.error("拦截异常:{}", exception);
		ModelAndView model = new ModelAndView();
		model.addObject("exception", exception);
		model.setViewName("error/500");
		return model;
	}

}
