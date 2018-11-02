package com.shanghai.modules.sys.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
* @author: YeJR 
* @version: 2018年5月10日 下午5:24:11
* 系统角色
*/
public class Role implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 角色id
	 */
	private Integer id;
	
	/**
	 * 角色名
	 */
	private String roleName;
	
	/**
	 * 是否管理员（0：不是， 1：是）
	 */
	private Integer isAdmin;
	
	/**
	 * 状态（0：不可用， 1：可用）
	 */
	private Integer status;
	
	/**
	 * 该角色拥有的菜单列表
	 */
	private List<Menu> menus = new ArrayList<Menu>();
	
	/**
	 * checkbox状态，专门为用户FORM页面显示角色时，设置的字段
	 */
	private String checkbox;
	
	public Role() {
		super();
	}

	
	public Role(Integer id) {
		super();
		this.id = id;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}
	
	public String getCheckbox() {
		return checkbox;
	}

	public void setCheckbox(String checkbox) {
		this.checkbox = checkbox;
	}

	/**
	 * 这两个方法供页面 ${role.menuIds} 调用
	 * @return
	 */
	@JsonIgnore
	public String getMenuIds() {
		List<Integer> menuIdList = new ArrayList<Integer>();
		for (Menu menu : menus) {
			menuIdList.add(menu.getId());
		}
		return StringUtils.join(menuIdList, ",");
	}
	
	@JsonIgnore
	public void setMenuIds(String menuIds) {
		menus = new ArrayList<Menu>();
		if (menuIds != null){
			String[] ids = StringUtils.split(menuIds, ",");
			for (String id : ids) {
				Menu menu = new Menu();
				menu.setId(Integer.parseInt(id));
				menus.add(menu);
			}
		}
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", roleName=" + roleName + ", isAdmin=" + isAdmin + ", status=" + status + ", menus="
				+ menus + ", checkbox=" + checkbox + "]";
	}


}
