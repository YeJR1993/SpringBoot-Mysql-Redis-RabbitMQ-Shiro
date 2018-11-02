package com.shanghai.common.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author YeJR
 * @version: 2018年5月28日 上午10:07:51
 * 重新shiro的UsernamePasswordToken，为其新增验证码属性
 *
 */
public class MyUsernamePasswordToken extends UsernamePasswordToken{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 验证码
	 */
	private String validateCode;

	public MyUsernamePasswordToken() {
		super();
		
	}
	
	 public MyUsernamePasswordToken(String username, String password, boolean rememberMe, String validateCode) {
		 super(username, password, rememberMe);
		 this.validateCode = validateCode;
	 }

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}
	
	 
}
