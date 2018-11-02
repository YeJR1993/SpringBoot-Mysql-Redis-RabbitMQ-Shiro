package com.shanghai.modules.sys.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
* @author: YeJR 
* @version: 2018年5月10日 下午5:43:11
* 菜单实体
*/
public class Menu implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	private Integer id;
	
	/**
	 * 父级菜单id
	 */
	private Integer parentId;
	
	/**
	 * 菜单名
	 */
	private String name;
	
	/**
	 * 排序
	 */
	private Integer sort;
	
	/**
	 * 菜单icon
	 */
	private String icon;
	
	/**
	 * 链接
	 */
	private String href;
	
	/**
	 * 是否显示（0：隐藏， 1：显示）
	 */
	private Integer isShow;
	
	/**
	 * 权限标识
	 */
	private String permission;
	
	/**
	 * 父菜单
	 */
	private Menu parent;
	
	/**
	 * 子菜单
	 */
	private List<Menu> children = new ArrayList<Menu>();
	
	public Menu() {
		super();
	}
	
	public Menu(Integer id) {
		super();
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}

	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}
	

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	/**
	 * 将从数据库获取到的Menu集合按照上下级和排序关系进行重排
	 * @param list
	 * @param sourcelist
	 * @param parentId
	 * @param cascade
	 */
	@JsonIgnore
	public static void sortList(List<Menu> list, List<Menu> sourceList, Integer parentId){
		for (int i = 0; i < sourceList.size(); i++){
			Menu menu = sourceList.get(i);
			if (menu.getParentId() == parentId){
				list.add(menu);
				// 重新循环从数据库中查询出来的sourcelist，看其中是否有该菜单的子菜单
				for (int j = 0; j < sourceList.size(); j++){
					Menu child = sourceList.get(j);
					if (child.getParentId() == menu.getId()){
						// 如果集合中有该菜单的子菜单，进行递归调用，将该菜单的子菜单优先加入到重排后的集合中
						sortList(list, sourceList, menu.getId());
						break;
					}
				}
			}
		}
	}

	@Override
	public String toString() {
		return "Menu [id=" + id + ", parentId=" + parentId + ", name=" + name + ", sort=" + sort + ", icon=" + icon
				+ ", href=" + href + ", isShow=" + isShow + ", permission=" + permission + ", parent=" + parent
				+ ", children=" + children + "]";
	}

	
}
