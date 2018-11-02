package com.shanghai.common.servlet;

import javax.servlet.annotation.WebServlet;

import com.alibaba.druid.support.http.StatViewServlet;

/**
 * @author YeJR
 * @version: 2018年5月28日 上午10:07:51
 * druid连接池监控Servlet
 * urlPatterns：监控地址
 * initParams = {@WebInitParam(name = "loginUsername", value = "root"),@WebInitParam(name = "loginPassword", value = "root")} 
 * 		账号密码暂时去除，因为有shiro的权限管理
 */
@WebServlet(urlPatterns = { "/druid/*" })
public class DruidStatViewServlet extends StatViewServlet {
	
	private static final long serialVersionUID = 1L;

}

