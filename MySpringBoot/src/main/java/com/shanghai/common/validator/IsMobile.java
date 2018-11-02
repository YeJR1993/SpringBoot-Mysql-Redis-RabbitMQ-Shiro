package com.shanghai.common.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @author YeJR
 * @version: 2018年5月28日 上午10:07:51
 * validate自定义手机号校验方法
 *
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {IsMobileValidator.class })
public @interface  IsMobile {
	
	/**
	 * 默认必须传入
	 * @return
	 */
	boolean required() default false;
	
	/**
	 * 校验不通过显示的信息
	 * @return
	 */
	String message() default "手机号码格式错误";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
