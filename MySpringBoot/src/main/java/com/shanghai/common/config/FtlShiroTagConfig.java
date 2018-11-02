package com.shanghai.common.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jagregory.shiro.freemarker.ShiroTags;

import freemarker.template.Configuration;

/**
* @author: YeJR 
* @version: 2018年5月18日 上午9:20:19
* Freemarker配置shiro标签
*/
@Component
public class FtlShiroTagConfig implements InitializingBean{
	
	@Autowired
	private Configuration configuration;

	@Override
	public void afterPropertiesSet() throws Exception {
		//可以在页面上使用shiro标签
		configuration.setSharedVariable("shiro", new ShiroTags());
	}

}
