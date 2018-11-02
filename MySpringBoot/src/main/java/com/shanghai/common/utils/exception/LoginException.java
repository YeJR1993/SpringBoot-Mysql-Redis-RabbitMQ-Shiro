package com.shanghai.common.utils.exception;

import com.shanghai.common.utils.CodeMsg;

/**
 * @author: YeJR
 * @version: 2018年6月1日 下午2:47:25
 * 自定义错误异常
 */
public class LoginException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CodeMsg codeMsg;

	public LoginException(CodeMsg codeMsg) {
		super(codeMsg.toString());
		this.codeMsg = codeMsg;
	}

	public CodeMsg getCodeMsg() {
		return codeMsg;
	}
}
