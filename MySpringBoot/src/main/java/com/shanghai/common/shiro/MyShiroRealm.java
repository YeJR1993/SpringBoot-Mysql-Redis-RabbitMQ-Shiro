package com.shanghai.common.shiro;

import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shanghai.common.redis.JedisUtils;
import com.shanghai.common.utils.DateTimeUtils;
import com.shanghai.common.utils.UserUtil;
import com.shanghai.common.utils.keyutils.SysModules;
import com.shanghai.modules.sys.entity.Menu;
import com.shanghai.modules.sys.entity.Role;
import com.shanghai.modules.sys.entity.User;

/**
 * @author: YeJR
 * @version: 2018年5月11日 下午4:04:35
 * 
 */
public class MyShiroRealm extends AuthorizingRealm {

	private final static Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);
	
	private final static String DOT = ",";
	
	/**
	 * 登录：在登录的时候需要将数据封装到Shiro的一个token中，执行shiro的login()方法，之后只要我们将SystemAuthorizingRealm这个类配置到Spring中，登录的时候Shiro就会自动的调用doGetAuthenticationInfo()方法进行验证。
	 * 验证当前登录的Subject
	 * 这里从数据库获取权限信息时,先去访问Spring3.1提供的缓存,而不使用Shior提供的AuthorizationCache 执行时机： Subject
	 * currentUser = SecurityUtils.getSubject(); currentUser.login(token);
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {

		logger.info("Shiro登录认证...");

		// 0. 把 AuthenticationToken 转换为 MyUsernamePasswordToken，保存了页面登录的账户密码
		MyUsernamePasswordToken token = (MyUsernamePasswordToken) authenticationToken;
		
		// 1.验证码比较
		Session session = SecurityUtils.getSubject().getSession();
		String validateCodeGenerate = JedisUtils.get(SysModules.validateCode, session.getId().toString(), String.class);
		String validateCodeForm = token.getValidateCode();
		if (validateCodeGenerate == null) {
			throw new AuthenticationException("验证码失效");
		}
		if (!validateCodeGenerate.equalsIgnoreCase(validateCodeForm)) {
			throw new AuthenticationException("验证码不匹配");
		}
		// 验证完之后删除该验证码
		JedisUtils.delete(SysModules.validateCode, session.getId().toString());
		
		// 2. 从 UsernamePasswordToken 中来获取 username
		String username = token.getUsername();

		// 3. 调用数据库的方法, 从数据库中查询 username 对应的用户记录
		User user = UserUtil.getByLoginName(username);

		// 4. 比较账户密码，抛出对应异常
		if (user == null) {
			throw new UnknownAccountException("不存在此用户！");
		}
		
		// 4.1 比较用户时间
		if (!user.isAdmin()) {
			try {
				if (!DateTimeUtils.compareWithSystemTime(user.getStartTime())) {
					throw new AuthenticationException("不在允许时间内！");
				}
				if (DateTimeUtils.compareWithSystemTime(user.getEndTime())) {
					throw new AuthenticationException("不在允许时间内！");
				}
			} catch (ParseException e) {
				throw new AuthenticationException("时间解析错误！");
			}
		}

		// 5. 根据用户的情况, 来构建 AuthenticationInfo 对象并返回. 通常使用的实现类为:
		// SimpleAuthenticationInfo; 密码的比对是由shiro完成
		// 1). principal: 认证的实体信息. 可以是 username, 也可以是数据表对应的用户的实体类对象.
		// 2). credentials: 数据库获取的用户密码.
		// 3). 盐值.
		ByteSource credentialsSalt = ByteSource.Util.bytes(username);
		// 4). realmName: 当前 realm 对象的 name. 调用父类的 getName() 方法即可

		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, user.getPassword(), credentialsSalt, getName());
		return simpleAuthenticationInfo;

	}

	/**
	 * 权鉴 执行时机： 需要实现缓存，不然会频繁认证
	 * 1、subject.hasRole(“admin”) 或 subject.isPermitted(“admin”)：自己去调用这个是否有什么角色或者是否有什么权限的时候；
	 * 2、@RequiresRoles("admin") ：在方法上加注解的时候；
	 * 3、[@shiro.hasPermission name ="admin"][/@shiro.hasPermission]：在页面上加shiro标签的时候，即进这个页面的时候扫描到有这个标签的时候。
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		logger.info("Shiro权限认证...");
		// 1. 从 PrincipalCollection 中来获取登录用户的信息(这里因为在上面认证的时候放入的是用户名)
		String username = (String) principalCollection.getPrimaryPrincipal();
		// 2. 利用用户名查询相关信息
		// 1).查询用户相关信息
		User user = UserUtil.getByLoginName(username);

		List<Menu> list = UserUtil.getMenuList(user.getId(), user.isAdmin(), null);

		// 2).添加角色和权限
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		for (Menu menu : list) {
			if (StringUtils.isNotBlank(menu.getPermission())) {
				// 添加基于Permission的权限信息
				for (String permission : StringUtils.split(menu.getPermission(), DOT)) {
					simpleAuthorizationInfo.addStringPermission(permission);
				}
			}
		}
		// 3).添加用户角色信息
		for (Role role : user.getRoles()) {
			if (role != null) {
				simpleAuthorizationInfo.addRole(role.getRoleName());
			}
		}

		return simpleAuthorizationInfo;
	}

}
