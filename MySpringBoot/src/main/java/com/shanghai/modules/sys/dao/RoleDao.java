package com.shanghai.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.shanghai.modules.sys.entity.Role;

/**
* @author: YeJR 
* @version: 2018年6月7日 上午11:06:17
* 
*/
@Mapper
public interface RoleDao {
	
	/**
	 * 通过主键ID获取对应角色信息
	 * @param role
	 * @return
	 */
	public Role get(Role role);
	
	/**
	 * 通过roleName获取对应角色信息
	 * @param roleName
	 * @return
	 */
	public Role getByRoleName(String roleName);
	
	/**
	 * 查询所有的角色
	 * @param role
	 * @return
	 */
	public List<Role> findList(Role role);
	
	/**
	 * 获取所有角色并对应用户
	 * @param userId
	 * @return
	 */
	public List<Role> findUserAllRole(Integer userId);
	
	/**
	 * 插入角色信息
	 * @param role
	 * @return
	 */
	public Integer insert(Role role);
	
	/**
	 * 更新角色信息
	 * @param role
	 * @return
	 */
	public Integer update(Role role);
	
	/**
	 * 删除角色信息
	 * @param role
	 * @return
	 */
	public Integer delete(Role role);
	
	/**
	 * 删除角色和菜单关联表数据
	 * @param role
	 * @return
	 */
	public Integer deleteRoleMenu(Role role);
	
	/**
	 * 插入角色和菜单关联表数据
	 * @param role
	 * @return
	 */
	public Integer insertRoleMenu(Role role);
	
	/**
	 * 角色和用户关联关系表
	 * @param role
	 * @return
	 */
	public Integer deleteRoleUser(Role role);
}
