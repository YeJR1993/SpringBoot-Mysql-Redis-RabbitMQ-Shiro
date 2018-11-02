package com.shanghai.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
* @author: YeJR 
* @version: 2018年4月27日 上午10:20:10
* spring boot 配置修改
*/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	
	/**
	 * 修改默认路径
	 * 访问http://localhost:8081/ 时直接跳转到http://localhost:8081/login
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("forward:/login");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
		WebMvcConfigurer.super.addViewControllers(registry);
	}
	
	/**
	 * 跨域配置
	 * 对需要的接口允许跨域访问，或者在需要允许的接口上
	 * 加上@CrossOrigin(origins = "http://localhost:8080")注解进行细粒度的配置
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// 允许http://localhost:8080域名下进行跨域访问api下面的接口
		registry.addMapping("/api/**").allowedOrigins("http://localhost:8080");
	}
	
}
