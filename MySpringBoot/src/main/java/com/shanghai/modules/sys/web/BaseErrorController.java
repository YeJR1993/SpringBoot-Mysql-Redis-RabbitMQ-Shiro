package com.shanghai.modules.sys.web;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author YeJR
 * @version 2018年6月22日 下午9:47:59
 * 自定义错误页面，防止在没有抛出异常的情况下出现 Whitelabel Error Page
 */
@Controller
@RequestMapping(value = "error")
public class BaseErrorController implements ErrorController{

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return "error/500";
	}
	
	@RequestMapping
	public String error() {
		return getErrorPath();
	}
	
}
