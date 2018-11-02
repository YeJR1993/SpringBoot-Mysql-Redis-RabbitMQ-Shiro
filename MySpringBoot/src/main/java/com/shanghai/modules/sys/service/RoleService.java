package com.shanghai.modules.sys.service;

import java.util.List;

import com.shanghai.common.utils.PageInfo;
import com.shanghai.modules.sys.entity.Role;

/**
* @author: YeJR 
* @version: 2018年6月15日 下午5:06:18
* 
*/
public interface RoleService {
	
	/**
	 * 通过角色ID获取角色信息
	 * @param id
	 * @return
	 */
	public Role getRoleById(Integer id);
	
	/**
	 * 分页查询
	 * @param pageNo
	 * @param pageSize
	 * @param role
	 * @return
	 */
	public PageInfo<Role> findUserByPage(int pageNo, int pageSize, Role role);
	
	/**
	 * 查询所有的角色
	 * @param role
	 * @return
	 */
	public List<Role> findAllList(Role role);
	
	/**
	 * 获取所有角色并对应用户
	 * @param userId
	 * @return
	 */
	public List<Role> findUserAllRole(Integer userId);
	
	/**
	 * 添加角色
	 * @param role
	 * @return
	 */
	public Integer saveRole(Role role);
	
	/**
	 * 更新角色
	 * @param role
	 * @return
	 */
	public Integer updateRole(Role role);
	
	/**
	 * 通过ID单独删除角色
	 * @param id
	 */
	public void deleteRoleById(Integer id);
	
	/**
	 * 批量删除角色
	 * @param ids
	 */
	public void deleteRoleByIds(Integer[] ids);
	
	/**
	 * 校验角色名
	 * @param roleName
	 * @param oldRoleName
	 * @return true：表示校验通过，角色名不重复
	 */
	public boolean verifyRoleName(String roleName, String oldRoleName);
	
	/**
	 * 更新角色权限菜单
	 * @param role
	 */
	public void authSave(Role role);

}
