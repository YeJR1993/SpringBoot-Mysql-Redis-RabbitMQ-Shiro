package com.shanghai.modules.sys.service;

import java.io.IOException;
import java.util.List;

import com.shanghai.common.utils.PageInfo;
import com.shanghai.modules.sys.entity.User;

/**
* @author: YeJR 
* @version: 2018年4月28日 上午10:18:39
* 
*/
public interface UserService {
	
	/**
	 * 通过登录名查询用户
	 * @param loginName
	 * @return
	 */
	public User getByLoginName(String loginName);
	
	/**
	 * 通过用户Id查询用户信息
	 * @param id
	 * @return
	 */
	public User getUserById(Integer id);
	
	/**
	 * 根据条件查询所有数据，导出
	 * @param user
	 * @return
	 */
	public List<User> findUsers(User user);
	
	/**
	 * 分页查询系统用户
	 * @param pageNo
	 * @param pageSize
	 * @param user
	 * @return
	 */
	public PageInfo<User> findUserByPage(int pageNo, int pageSize, User user);
	
	/**
	 * 添加用户
	 * @param user
	 * @return
	 * @throws IOException 
	 */
	public Integer saveUser(User user) throws IOException;
	
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 * @throws IOException 
	 */
	public Integer updateUser(User user) throws IOException;
	
	/**
	 * 通过用户Id删除用户
	 * @param id
	 * @throws IOException
	 */
	public void deleteUserById(Integer id) throws IOException;
	
	/**
	 * 批量删除用户
	 * @param ids
	 * @throws IOException 
	 */
	public void deleteUserByIds(Integer[] ids) throws IOException;
	
	/**
	 * 校验用户名是否已经存在
	 * @param username
	 * @param oldUsername
	 * @return true:校验通过，表示该用户名没有重复
	 */
	public boolean verifyUsername(String username, String oldUsername);
	
}
