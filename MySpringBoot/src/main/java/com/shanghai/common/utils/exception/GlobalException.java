package com.shanghai.common.utils.exception;

import com.shanghai.common.utils.CodeMsg;

/**
 * @author YeJR
 * @version: 2018年5月28日 上午10:07:51
 * 自定义全局异常
 *
 */
public class GlobalException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CodeMsg codeMsg;
	
	public GlobalException(CodeMsg codeMsg) {
		super(codeMsg.toString());
		this.codeMsg = codeMsg;
	}

	public CodeMsg getCodeMsg() {
		return codeMsg;
	}

}
