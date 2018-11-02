package com.shanghai.common.utils.keyutils;

import com.shanghai.common.redis.rediskey.BasePrefix;
import com.shanghai.common.utils.constant.SysConstants;

/**
 * @author YeJR
 * @version: 2018年5月28日 上午10:07:51
 * 每个模块需要定义不同的key，防止在保存到redis时出现key重复覆盖的现象
 */
public class SysModules extends BasePrefix{

	private SysModules(String prefix) {
		super(prefix);
	}
	
	private SysModules(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}
	/**
	 * 登录验证码
	 */
	public static SysModules validateCode = new SysModules(SysConstants.VALIDATECODE_EXPIRETIME, "validateCode");
	
	
}
