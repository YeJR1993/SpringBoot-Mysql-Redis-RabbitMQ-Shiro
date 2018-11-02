package com.shanghai.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * @author YeJR
 * @date: 2018年2月8日 上午11:00:40
 * 方便获得ApplicationContext中的所有bean
 * 
 * ApplicationContextAware:
 * 当一个类实现了这个接口（ApplicationContextAware）之后，这个类就可以方便获得ApplicationContext中的所有bean。
 * 换句话说，就是这个类可以直接获取spring配置文件中，所有有引用到的bean对象。
 * DisposableBean:
 * 如果实现了DisposebleBean接口，那么Spring将自动调用bean中的Destory方法进行销毁，与在配置文件中配置destory-method的情况与上面相同
 */
@Service
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {
	
	/**
	 * 当前项目上下文
	 */
	private static ApplicationContext applicationContext = null;
	
	/**
	 * 实现ApplicationContextAware接口, 注入spring配置文件中所有的bean对象到静态变量中.
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextHolder.applicationContext = applicationContext;
	}
	
	/**
	 * 实现DisposableBean接口, 在Context关闭时清理静态变量.
	 */
	@Override
	public void destroy() throws Exception {
		SpringContextHolder.clearHolder();
	}

	/**
	 * 清除SpringContextHolder中的ApplicationContext
	 * 提取出来，方便其他地方调用
	 */
	public static void clearHolder() {
		applicationContext = null;
	}

	/**
	 * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型
	 * @param requiredType
	 * @return
	 */
	public static <T> T getBean(Class<T> requiredType) {
		return applicationContext.getBean(requiredType);
	}
}
