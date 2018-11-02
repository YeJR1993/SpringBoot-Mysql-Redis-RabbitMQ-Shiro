package com.shanghai.common.handler;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.shanghai.common.utils.CodeMsg;
import com.shanghai.common.utils.DateTimeUtils;
import com.shanghai.common.utils.exception.GlobalException;

/**
 * @author: YeJR
 * @version: 2018年7月26日 下午3:59:14
 * 全局handler前日期统一处理 : 
 * 		使用正则表达式将前端的String 类型转为Date类型
 * 		实体中如果时间变量是使用的String类型，也不会有影响
 */
@Component
public class DateConverterHandler implements Converter<String, Date> {

	private static final List<String> FORMARTS = new ArrayList<String>(4);
	
	static {
		FORMARTS.add("yyyy-MM");
		FORMARTS.add("yyyy-MM-dd");
		FORMARTS.add("yyyy-MM-dd hh:mm");
		FORMARTS.add("yyyy-MM-dd hh:mm:ss");
	}
	
	@Override
	public Date convert(String source) {
		String value = source.trim();
		if ("".equals(value)) {
			return null;
		}
		try {
			if (value.matches("^\\d{4}-\\d{1,2}$")) {
				return DateTimeUtils.parseDate(value, FORMARTS.get(0));
			} else if (value.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
				return DateTimeUtils.parseDate(value, FORMARTS.get(1));
			} else if (value.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")) {
				return DateTimeUtils.parseDate(value, FORMARTS.get(2));
			} else if (value.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
				return DateTimeUtils.parseDate(value, FORMARTS.get(3));
			} else {
				throw new IllegalArgumentException("Invalid boolean value '" + value + "'");
			}
		} catch (ParseException e) {
			e.printStackTrace();
			throw new GlobalException(CodeMsg.ANALYSIS_DATE_ERROR);
		}
		
	}


}
