package com.shanghai.common.validator;
import  javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.shanghai.common.utils.ValidateRegular;

/**
 * @author YeJR
 * @version: 2018年5月28日 上午10:07:51
 * validate自定义手机号校验方法
 *
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

	/**
	 * 是否必填
	 */
	private boolean required = false;
	
	/**
	 * 初始化
	 */
	@Override
	public void initialize(IsMobile constraintAnnotation) {
		required = constraintAnnotation.required();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		//若必填
		if(required) {
			return ValidateRegular.isMobile(value);
		}else {
			if(StringUtils.isEmpty(value)) {
				return true;
			}else {
				return ValidateRegular.isMobile(value);
			}
		}
	}

}
