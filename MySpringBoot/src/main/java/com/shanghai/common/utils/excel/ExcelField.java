package com.shanghai.common.utils.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* @author: YeJR 
* @version: 2018年7月13日 下午2:30:10
* Excel注解定义 参考 Jeeplus
* @Target ： 表示该注解可以用于什么地方， 可能的ElementType参数有：
* 		CONSTRUCTOR：构造器的声明
*		FIELD：域声明（包括enum实例）
*		LOCAL_VARIABLE：局部变量声明
*		METHOD：方法声明
*		PACKAGE：包声明
*		PARAMETER：参数声明
*		TYPE：类、接口（包括注解类型）或enum声明	
* @Retention ：  表示需要在什么级别保存该注解信息。可选的RetentionPolicy参数包括：
* 				SOURCE：注解将被编译器丢弃
*				CLASS：注解在class文件中可用，但会被VM丢弃
*				RUNTIME：VM将在运行期间保留注解，因此可以通过反射机制读取注解的信息
*
* @Document ： 将注解包含在Javadoc中
* @Inherited ： 允许子类继承父类中的注解
*/
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelField {

	/**
	 * 导出字段名（默认调用当前字段的“get”方法，如指定导出字段为对象，请填写“对象名.对象属性”，例：“area.name”、“office.name”）
	 */
	String value() default "";
	
	/**
	 * 导出字段标题（需要添加批注请用“**”分隔，标题**批注，仅对导出模板有效）
	 */
	String title();
	
	/**
	 * 字段类型（0：导出导入；1：仅导出；2：仅导入）
	 */
	int type() default 0;

	/**
	 * 导出字段对齐方式（0：自动；1：靠左；2：居中；3：靠右）
	 */
	int align() default 0;
	
	/**
	 * 导出字段字段排序（升序）
	 */
	int sort() default 0;

	/**
	 * 如果是字典类型，请设置字典的type值
	 */
	String dictType() default "";
	
	/**
	 * 反射类型
	 */
	Class<?> fieldType() default Class.class;
	
	/**
	 * 字段归属组（根据分组导出导入）
	 */
	int[] groups() default {};
	
}
