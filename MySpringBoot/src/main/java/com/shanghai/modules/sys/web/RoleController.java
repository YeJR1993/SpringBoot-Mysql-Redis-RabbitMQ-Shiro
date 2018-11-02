package com.shanghai.modules.sys.web;

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
import com.shanghai.modules.sys.entity.Menu;
import com.shanghai.modules.sys.entity.Role;
import com.shanghai.modules.sys.service.MenuService;
import com.shanghai.modules.sys.service.RoleService;

/**
 * @author YeJR
 * @version 2018年6月22日 下午8:04:36
 *
 */
@Controller
@RequestMapping(value = "sys/role")
public class RoleController extends BaseController{
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private MenuService menuService;
	
	/**
	 * list列表
	 * @param role
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "list")
	@RequiresPermissions(value="sys:list:role")
	public String list(Role role, Model model) {
		PageInfo<Role> page = roleService.findUserByPage(getPageNo(), getPageSize(), role);
		model.addAttribute("page", page);
		return "modules/sys/roleList";
	}
	
	/**
	 * form表单
	 * @param role
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "form")
	@RequiresPermissions(value= {"sys:add:role", "sys:view:role", "sys:edit:role"}, logical=Logical.OR)
	public String form(Role role, Model model) {
		if (role.getId() != null) {
			role = roleService.getRoleById(role.getId());
			model.addAttribute("role", role);
		}
		return "modules/sys/roleForm";
	}
	
	/**
	 * 添加或者编辑
	 * @param role
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "save")
	@RequiresPermissions(value= {"sys:add:role", "sys:edit:role"}, logical=Logical.OR)
	public String save(Role role, RedirectAttributes redirectAttributes) {
		if (role.getId() == null) {
			roleService.saveRole(role);
		} else {
			roleService.updateRole(role);
		}
		redirectAttributes.addFlashAttribute("msg", SysConstants.OPERATE_SUCCESS_PAGE_TIP);
		return "redirect:/sys/role/list";
	}

	/**
	 * 单个删除
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "delete")
	@RequiresPermissions(value="sys:delete:role")
	public String delete(Integer id, RedirectAttributes redirectAttributes) {
		roleService.deleteRoleById(id);
		redirectAttributes.addFlashAttribute("msg", SysConstants.OPERATE_SUCCESS_PAGE_TIP);
		return "redirect:/sys/role/list";
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "deleteAll")
	@RequiresPermissions(value="sys:delete:user")
	public String deleteAll(Integer[] ids, RedirectAttributes redirectAttributes) {
		roleService.deleteRoleByIds(ids);
		redirectAttributes.addFlashAttribute("msg", SysConstants.OPERATE_SUCCESS_PAGE_TIP);
		return "redirect:/sys/role/list";
	}
	
	/**
	 * 校验角色名是不是新的
	 * @param roleName
	 * @param oldRoleName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "verifyRoleName")
	public boolean verifyUsername (String roleName, String oldRoleName) {
		return roleService.verifyRoleName(roleName, oldRoleName);
	}
	
	/**
	 * 进入权限设置页面
	 * @param role
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "auth")
	@RequiresPermissions(value="sys:allocation:role")
	public String auth (Role role, Model model) {
		role = roleService.getRoleById(role.getId());
		model.addAttribute("role", role);
		model.addAttribute("menuList", menuService.findAllMenus(new Menu()));
		return "modules/sys/roleAuth";
	}
	
	/**
	 * 权限保存
	 * @param role
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "authSave")
	@RequiresPermissions(value="sys:allocation:role")
	public String authSave (Role role, RedirectAttributes redirectAttributes) {
		roleService.authSave(role);
		redirectAttributes.addFlashAttribute("msg", SysConstants.OPERATE_SUCCESS_PAGE_TIP);
		return "redirect:/sys/role/list";
	}
	
	
}
