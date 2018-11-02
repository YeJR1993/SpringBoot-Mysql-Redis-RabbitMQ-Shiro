package com.shanghai.common.utils;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.shanghai.common.utils.constant.TimeConstants;

/**
 * @author: YeJR
 * @version: 2018年7月24日 下午4:29:25 时间工具类
 */
public class DateTimeUtils extends DateUtils{	
	
	/**
	 * 格式化时间
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date, String format) {
		String formatDate = DateFormatUtils.format(date, format);
		return formatDate;
	}
	
	/**
	 * 获取服务器当前时间
	 * @return
	 */
	public static String getServerTime() {
		Date today = new Date();
		return formatDate(today, TimeConstants.YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * 获取服务器当前时间
	 * @param format  时间格式
 	 * @return
	 */
	public static String getServerTime(String format) {
		Date today = new Date();
		return formatDate(today, format);
	}
	
	/**
	 * 与系统当前时间比较
	 * @param time
	 * @param format time 对应的格式
	 * @return  true：在当前系统时间之前；  false：在当前系统时间之后
	 * @throws ParseException
	 */
	public static Boolean compareWithSystemTime(String time, String format) throws ParseException {
		Date timeDate = parseDate(time, format);
		Date currentDate = new Date();
		if (timeDate.before(currentDate)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 与系统当前时间比较
	 * @param time
	 * @return  true：在当前系统时间之前；  false：在当前系统时间之后
	 * @throws ParseException 
	 */
	public static Boolean compareWithSystemTime(Date time) throws ParseException {
		Date currentDate = new Date();
		if (time.before(currentDate)) {
			return true;
		}
		return false;
	}

	/**
	 * 两时间比较
	 * @param time1
	 * @param time2
	 * @param format 时间格式
	 * @return true：time1 在 time2 之前；  false：time1 在 time2 之后
	 * @throws ParseException
	 */
	public static Boolean compareTime(String time1, String time2, String format) throws ParseException {
		Date time1Date = parseDate(time1, format);
		Date time2Date = parseDate(time2, format);
		if (time1Date.before(time2Date)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 两时间比较
	 * @param time1
	 * @param time2
	 * @return true：time1 在 time2 之前；  false：time1 在 time2 之后
	 * @throws ParseException
	 */
	public static Boolean compareTime(Date time1, Date time2) throws ParseException {
		if (time1.before(time2)) {
			return true;
		}
		return false;
	}
	
}
