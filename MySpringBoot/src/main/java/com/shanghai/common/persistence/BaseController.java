package com.shanghai.common.persistence;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author YeJR
 * @version 2018年6月20日 上午10:07:51
 * controller基类
 */
public abstract class BaseController {

	@Autowired
	HttpServletRequest request;
	
	/**
	 * 默认页码
	 */
	private Integer defaultPageNo = 1;
	
	/**
	 * 默认每页条数
	 */
	private Integer defaultPageSize = 10;
	
	/**
	 * 获取分页pageNo
	 * @return
	 */
	protected Integer getPageNo() {
		Session session = SecurityUtils.getSubject().getSession();
		//先从request中获取
		String pageNoStr = request.getParameter("pageNo");
		String param = "pageNo:" + request.getServletPath();
		//能获取到
		if (StringUtils.isNotBlank(pageNoStr)) {
			//保存到session中
			session.setAttribute(param, pageNoStr);
		} else {
			// 若没有，先从session中获取
			if (session.getAttribute(param) == null) {
				return defaultPageNo;
			} else {
				pageNoStr = String.valueOf(session.getAttribute(param));
			}
		}
		return Integer.parseInt(pageNoStr);
	}
	
	/**
	 * 获取分页pageSize
	 * @return
	 */
	protected Integer getPageSize() {
		Session session = SecurityUtils.getSubject().getSession();
		//先从request中获取
		String pageSizeStr = request.getParameter("pageSize");
		String param = "pageSize:" + request.getServletPath();
		//能获取到
		if (StringUtils.isNotBlank(pageSizeStr)) {
			//保存到session中
			session.setAttribute(param, pageSizeStr);
		} else {
			// 若没有，先从session中获取
			if (session.getAttribute(param) == null) {
				return defaultPageSize;
			} else {
				pageSizeStr = String.valueOf(session.getAttribute(param));
			}
		}
		return Integer.parseInt(pageSizeStr);
	}
	
}
