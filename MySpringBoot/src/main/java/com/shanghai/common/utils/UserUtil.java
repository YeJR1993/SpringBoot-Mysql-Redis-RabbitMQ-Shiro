package com.shanghai.common.utils;

import java.util.List;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import com.shanghai.common.utils.constant.SysConstants;
import com.shanghai.modules.sys.entity.Menu;
import com.shanghai.modules.sys.entity.User;
import com.shanghai.modules.sys.service.MenuService;
import com.shanghai.modules.sys.service.UserService;

/**
* @author: YeJR 
* @version: 2018年5月11日 下午4:29:21
* 用户工具类
*/
public class UserUtil {
	
	/**
	 * 获取对应的bean
	 */
	private static UserService userService = SpringContextHolder.getBean(UserService.class);
	private static MenuService menuService = SpringContextHolder.getBean(MenuService.class);
	
	/**
	 * 根据登录名获取用户
	 * @param loginName
	 * @return 取不到返回null
	 */
	public static User getByLoginName(String loginName){
		return userService.getByLoginName(loginName);
	}

	/**
	 * 查询该用户id对应下的所有菜单列表
	 * @param userId
	 * @param isAdmin
	 * @param isShow null:所有菜单， 0：隐藏的菜单， 1：显示的菜单
	 * @return
	 */
	public static List<Menu> getMenuList(Integer userId, Boolean isAdmin, Integer isShow) {
		return menuService.getUserMenuList(userId, isAdmin, isShow);
	}
	
	/**
	 * 登录密码加密，使用shiro的盐值加密方法
	 * @param name
	 * @param password
	 * @return
	 */
	public static String encryptPassword(String name, String password) {
		Object salt = ByteSource.Util.bytes(name);
		Object newPassword = new SimpleHash(SysConstants.SHIRO_CREDENTIALSMATCHER, password, salt, SysConstants.SHIRO_ENCRYPTION_NUMBER);
		return newPassword.toString();
	}
	
	
}
