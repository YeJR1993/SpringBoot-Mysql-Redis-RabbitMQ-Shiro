package com.shanghai.common.filter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import com.alibaba.druid.support.http.WebStatFilter;

/**
 * @author YeJR
 * @version: 2018年5月28日 上午10:07:51
 * druid连接池监控Filter
 * urlPatterns：拦截所有
 * initParams：不拦截
 */
@WebFilter(filterName = "druidWebStatFilter", urlPatterns = "/*", initParams = {@WebInitParam(name = "exclusions", value = "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")})
public class DruidWebStatFilter extends WebStatFilter {

}
