package com.shanghai.modules.sys.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shanghai.common.persistence.BaseController;
import com.shanghai.common.utils.PageInfo;
import com.shanghai.common.utils.constant.SysConstants;
import com.shanghai.common.utils.excel.ExportExcel;
import com.shanghai.modules.sys.entity.Role;
import com.shanghai.modules.sys.entity.User;
import com.shanghai.modules.sys.service.RoleService;
import com.shanghai.modules.sys.service.UserService;

/**
 * @author YeJR
 * @version 2018年5月26日 下午11:28:24
 * 用户Controller
 */
@Controller
@RequestMapping(value = "sys/user")
public class UserController extends BaseController{

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	/**
	 * list列表 
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "list")
	@RequiresPermissions(value="sys:list:user")
	public String list(User user, Model model) {
		PageInfo<User> page = userService.findUserByPage(getPageNo(), getPageSize(), user);
		model.addAttribute("page", page);
		return "modules/sys/userList";
	}
	
	/**
	 * form表单
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "form")
	@RequiresPermissions(value= {"sys:add:user", "sys:view:user", "sys:edit:user"}, logical=Logical.OR)
	public String form(User user, Model model) {
		if (user.getId() != null) {
			user = userService.getUserById(user.getId());
			model.addAttribute("user", user);
			model.addAttribute("allRoles", roleService.findUserAllRole(user.getId()));
		} else {
			Role role = new Role();
			role.setStatus(1);
			model.addAttribute("allRoles", roleService.findAllList(role));
		}
		return "modules/sys/userForm";
	}
	
	/**
	 * 添加或者编辑
	 * @param user
	 * @param redirectAttributes
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "save")
	@RequiresPermissions(value= {"sys:add:user", "sys:edit:user"}, logical=Logical.OR)
	public String save(User user, RedirectAttributes redirectAttributes) throws IOException {
		if (user.getId() == null) {
			userService.saveUser(user);
		} else {
			userService.updateUser(user);
		}
		redirectAttributes.addFlashAttribute("msg", SysConstants.OPERATE_SUCCESS_PAGE_TIP);
		return "redirect:/sys/user/list";
	}
	
	/**
	 * 单个删除
	 * @param id
	 * @param redirectAttributes
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "delete")
	@RequiresPermissions(value="sys:delete:user")
	public String delete(Integer id, RedirectAttributes redirectAttributes) throws IOException {
		userService.deleteUserById(id);
		redirectAttributes.addFlashAttribute("msg", SysConstants.OPERATE_SUCCESS_PAGE_TIP);
		return "redirect:/sys/user/list";
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @param redirectAttributes
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "deleteAll")
	@RequiresPermissions(value="sys:delete:user")
	public String deleteAll(Integer[] ids, RedirectAttributes redirectAttributes) throws IOException {
		userService.deleteUserByIds(ids);
		redirectAttributes.addFlashAttribute("msg", SysConstants.OPERATE_SUCCESS_PAGE_TIP);
		return "redirect:/sys/user/list";
	}
	
	/**
	 * 导出Excel
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "export")
	@RequiresPermissions(value="sys:export:user")
	public String export(User user, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws IOException {
		String fileName = "用户数据" + System.currentTimeMillis() + ".xlsx";
        List<User> list = userService.findUsers(user);
 		new ExportExcel("用户数据", User.class).setDataList(list).write(response, fileName).dispose();
 		return null;
	}
	
	/**
	 * 校验用户名是不是新的
	 * @param username
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "verifyUsername")
	public boolean verifyUsername (String username, String oldUsername) {
		return userService.verifyUsername(username,oldUsername);
	}
	
}
