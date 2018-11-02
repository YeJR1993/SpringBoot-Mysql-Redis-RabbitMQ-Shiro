package com.shanghai.common.tag;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shanghai.common.utils.SpringContextHolder;
import com.shanghai.modules.sys.entity.Menu;
import com.shanghai.modules.sys.entity.User;
import com.shanghai.modules.sys.service.MenuService;
import com.shanghai.modules.sys.service.UserService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * @author: YeJR
 * @version: 2018年5月31日 上午11:31:08 自定义菜单标签类
 */
@Component
public class MenuTag implements TemplateDirectiveModel {

	@Autowired
	private UserService userService;

	@Autowired
	private MenuService menuService;

	@Override
	@SuppressWarnings("rawtypes")
	public void execute(Environment environment, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		// 获取当前登入的用户名
		String loginName = SecurityUtils.getSubject().getPrincipal().toString();
		// 获取对应用户信息
		User user = userService.getByLoginName(loginName);
		// 查询出当前用户拥有的所有需要在左侧显示的菜单
		List<Menu> menus = menuService.getUserMenuList(user.getId(), user.isAdmin(), 1);
		// 菜单
		StringBuffer menuString = new StringBuffer();

		if (menus != null && menus.size() > 0) {
			menuString.append("<div class=\"tpl-left-nav tpl-left-nav-hover menu\"><div class=\"tpl-left-nav-title\">菜单列表</div><div class=\"tpl-left-nav-list\"><ul class=\"tpl-left-nav-menu\">");
			menuTree(menus, menuString, 1, 1);
			menuString.append("</ul></div></div>");
		}
		// 将菜单信息输出到页面
		// 使用最顶级菜单以及下面的子菜单，不能区分开用户的菜单。所以这里暂时使用手动筛选
		environment.getOut().write(menuString.toString());
	}

	/**
	 * 生成页面左侧的菜单，这里菜单暂时只到二级
	 * 
	 * @param menu
	 * @return
	 */
	private void menuTree(List<Menu> menus, StringBuffer menuString, Integer parentId, Integer index) {
		for (int i = 0; i < menus.size(); i++) {
			Menu menu = menus.get(i);
			// 如果当前菜单的父级ID和需要比较的父级菜单一致
			if (menu.getParentId() == parentId) {
				// 当前菜单是否有子菜单
				Boolean hasChild = false;
				// href 和 icon
				String href = "#";
				String icon = menu.getIcon();
				// 重新循环从menus，看menus中是否有该菜单的子菜单
				for (int j = 0; j < menus.size(); j++) {
					Menu child = menus.get(j);
					if (child.getParentId() == menu.getId()) {
						// 如果集合中有该菜单的子菜单，进行递归调用，将该菜单的子菜单优先加入到重排后的集合中
						hasChild = true;
						break;
					}
				}
				
				if (StringUtils.isNotBlank(menu.getHref())) {
					ServletContext context = SpringContextHolder.getBean(ServletContext.class);
					href = context.getContextPath() + menu.getHref();
				}
				if (index == 1) {
					if (hasChild) {
						menuString.append("<li class=\"tpl-left-nav-item\"><a href=\"javascript:;\" class=\"nav-link tpl-left-nav-link-list\">");
						if (StringUtils.isNotBlank(icon)) {
							menuString.append("<i class=\"" + icon + "\"></i>");
						}
						menuString.append("<span>" + menu.getName() + "</span><i class=\"am-icon-angle-right tpl-left-nav-more-ico am-fr am-margin-right\"></i></a><ul class=\"tpl-left-nav-sub-menu\"><li>");
					} else {
						menuString.append("<li class=\"tpl-left-nav-item\"><a href=\"" + href + "\" class=\"nav-link YeJR_menuItem \">");
						if (StringUtils.isNotBlank(icon)) {
							menuString.append("<i class=\"" + icon + "\"></i>");
						}
						menuString.append("<span>" + menu.getName() + "</span></a></li>");
					}
				}
				if (index == 2) {
					menuString.append("<a class=\"YeJR_menuItem\" href=\"" + href + "\">");
					if (StringUtils.isNotBlank(icon)) {
						menuString.append("<i class=\"" + icon + "\"></i>");
					}
					menuString.append("<span>" + menu.getName() + "</span></a>");
				}
				// 如果有子菜单
				if (hasChild) {
					menuTree(menus, menuString, menu.getId(), index + 1);
				}
				if (index == 1) {
					menuString.append("</li></ul></li>");
				}
			}
		}
	}

}
