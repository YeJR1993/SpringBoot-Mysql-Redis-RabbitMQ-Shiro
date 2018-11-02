package com.shanghai.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.Page;
import com.shanghai.modules.sys.entity.User;

/**
* @author: YeJR 
* @version: 2018年4月23日 下午4:25:30
* 
*/
@Mapper
public interface UserDao {
	
	/**
	 * 通过用户名查询用户
	 * @param name
	 * @return
	 */
	public User getUserByName(String name);
	
	/**
	 * 通过主键ID查询用户信息
	 * @param user
	 * @return
	 */
	public User get(User user);
	
	/**
	 * 分页查询用户数据
	 * @param user
	 * @return
	 */
	public Page<User> findList(User user);
	
	/**
	 * 保存用户信息
	 * @param user
	 * @return
	 */
	public Integer insert(User user);
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	public Integer update(User user);
	
	/**
	 * 删除用户数据
	 * @param user
	 * @return
	 */
	public Integer delete(User user);
	
	/**
	 * 删除该用户对应的所有角色
	 * @param user
	 * @return
	 */
	public Integer deleteUserRole(User user);
	
	/**
	 * 保存该用户对应的所有角色
	 * @param user
	 * @return
	 */
	public Integer insertUserRole(User user);
}
