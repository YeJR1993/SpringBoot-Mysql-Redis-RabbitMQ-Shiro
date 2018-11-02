package com.shanghai.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author YeJR
 * @version: 2018年5月28日 上午10:07:51
 * validation的正则校验工具方法
 *
 */
public class ValidateRegular {

	/**
	 * 手机号验证
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isMobile(String str) {
		boolean result = false;
		// 验证手机号
		String regex = "^[1][3,4,5,7,8][0-9]{9}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		result = matcher.matches();
		return result;
	}
	
	/**
	 * 电话号码校验
	 * @param str
	 * @return
	 */
	public static boolean isPhone(String str) {
		String regexWithZone = "^[0][1-9]{2,3}-[0-9]{5,10}$";
		String regexWithOutZone = "^[1-9]{1}[0-9]{5,8}$";
		//号码长度
		int length = 9;
		// 验证带区号的
		Pattern patternWithZone = Pattern.compile(regexWithZone); 
		// 验证没有区号的
		Pattern patternWithOutZone = Pattern.compile(regexWithOutZone); 
		
		boolean result = false;
		Matcher matcher = null;
		
		if (str.length() > length) {
			matcher = patternWithZone.matcher(str);
			result = matcher.matches();
		} else {
			matcher = patternWithOutZone.matcher(str);
			result = matcher.matches();
		}
		return result;
	}
}
