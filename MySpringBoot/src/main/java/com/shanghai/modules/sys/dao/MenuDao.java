package com.shanghai.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.shanghai.modules.sys.entity.Menu;

/**
* @author: YeJR 
* @version: 2018年5月16日 下午2:24:09
* 
*/
@Mapper
public interface MenuDao {

	/**
	 * 查询当前用户的角色对应的菜单
	 * @param userId
	 * @param isShow null:包括显示和隐藏的菜单， 0：隐藏的菜单， 1显示的菜单
	 * @return
	 */
	public List<Menu> findByUserId(@Param(value="userId")Integer userId, @Param(value="isShow") Integer isShow);
	
	/**
	 * 查询所有菜单
	 * @param menu
	 * @return
	 */
	public List<Menu> findList(Menu menu);
	
	/**
	 * 根据某个字段进行查询
	 * @param column
	 * @param value
	 * @return
	 */
	public  Menu findUniqueByProperty(@Param(value="column")String column, @Param(value="value")Object value);
	
	/**
	 * 通过菜单ID查询菜单信息
	 * @param menu
	 * @return
	 */
	public Menu get(Menu menu);
	
	/**
	 * 插入
	 * @param menu
	 * @return
	 */
	public Integer insert(Menu menu);
	
	/**
	 * 更新
	 * @param menu
	 * @return
	 */
	public Integer update(Menu menu);
	
	/**
	 * 根据ID删除菜单
	 * @param menu
	 * @return
	 */
	public Integer delete(Menu menu); 
	
	/**
	 * 删除菜单角色对应关系
	 * @param menu
	 * @return
	 */
	public Integer deleteMenuRole(Menu menu);
	
}
