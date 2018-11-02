package com.shanghai.common.utils;

/**
 * @author YeJR
 * @version: 2018年5月28日 上午10:07:51
 * 统一返回对象
 * @param <T>
 */
public class Result<T> {
	
	/**
	 * 返回码
	 */
	private int code;
	
	/**
	 * 信息
	 */
	private String msg;
	
	/**
	 * 数据
	 */
	private T data;
	
	/**
	 *  成功时候的调用
	 * */
	public static  <T> Result<T> success(T data){
		return new Result<T>(CodeMsg.SUCCESS, data);
	}
	
	/**
	 *  失败时候的调用
	 * */
	public static  <T> Result<T> error(CodeMsg codeMsg){
		return new Result<T>(codeMsg);
	}
	
	private Result(T data) {
		this.data = data;
	}
	
	private Result(CodeMsg codeMsg, T data) {
		this.data = data;
		if(codeMsg != null) {
			this.code = codeMsg.getCode();
			this.msg = codeMsg.getMsg();
		}
	}
	
	private Result(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	private Result(CodeMsg codeMsg) {
		if(codeMsg != null) {
			this.code = codeMsg.getCode();
			this.msg = codeMsg.getMsg();
		}
	}
	
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
}
