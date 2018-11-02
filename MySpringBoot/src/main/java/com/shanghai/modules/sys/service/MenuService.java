package com.shanghai.modules.sys.service;
/**
* @author: YeJR 
* @version: 2018年6月25日 下午5:06:55
* 
*/

import java.util.List;

import com.shanghai.modules.sys.entity.Menu;

public interface MenuService {
	
	/**
	 * 获取用户对应的显示的菜单列表
	 * @param userId
	 * @param isAdmin
	 * @param isShow  null:所有菜单， 0：隐藏的菜单， 1：显示的菜单
	 * @return
	 */
	public List<Menu> getUserMenuList(Integer userId, Boolean isAdmin, Integer isShow);
	
	/**
	 * 查询所有菜单, 并进行排序
	 * @param menu
	 * @return
	 */
	public List<Menu> findAllMenus(Menu menu);

	/**
	 * 通过ID获取menu
	 * @param menu
	 * @return
	 */
	public Menu getMenuById(Menu menu);
	
	/**
	 * 保存菜单信息
	 * @param menu
	 * @return
	 */
	public Integer saveMenu(Menu menu);
	
	/**
	 * 更新菜单信息
	 * @param menu
	 * @return
	 */
	public Integer updateMenu(Menu menu);
	
	/**
	 * 根据菜单ID删除
	 * @param menu
	 */
	public void deleteMenuById(Integer id);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void deletemenuByIds(Integer ids[]);
	
}
