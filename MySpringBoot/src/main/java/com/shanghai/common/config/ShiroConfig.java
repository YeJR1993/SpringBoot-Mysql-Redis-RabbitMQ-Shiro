package com.shanghai.common.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.shanghai.common.shiro.MyShiroRealm;
import com.shanghai.common.shiro.ShiroRedisCacheManager;
import com.shanghai.common.shiro.ShiroRedisSessionDao;
import com.shanghai.common.utils.constant.SysConstants;

import redis.clients.jedis.JedisPool;

/**
 * @author: YeJR
 * @version: 2018年5月11日 下午3:41:43
 * shiro配置
 */

@Configuration
public class ShiroConfig {

	/**
	 * session 过期时间
	 */
	@Value("${shiro.session.expireTime}")
	private int sessionExpireTime;

	/**
	 * cache过期时间
	 */
	@Value("${shiro.cache.expireTime}")
	private int cacheExpireTime;

	@Autowired
	JedisPool jedisPool;

	/**
	 * 定义一个bean，不然下面的构造方法出错
	 * 
	 * @return
	 */
	@Bean(name = { "sessionExpireTime" })
	public int getSessionExpireTime() {
		return sessionExpireTime;
	}

	@Bean(name = { "cacheExpireTime" })
	public int getCacheExpireTime() {
		return cacheExpireTime;
	}

	/**
	 * 注入自定义的Realm
	 * 
	 * @return
	 */
	@Bean
	public MyShiroRealm myShiroRealm() {
		MyShiroRealm myShiroRealm = new MyShiroRealm();

		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
		// 指定shiro密码比较的加密算法
		credentialsMatcher.setHashAlgorithmName(SysConstants.SHIRO_CREDENTIALSMATCHER);
		// 指定加密次数
		credentialsMatcher.setHashIterations(SysConstants.SHIRO_ENCRYPTION_NUMBER);

		myShiroRealm.setCredentialsMatcher(credentialsMatcher);

		return myShiroRealm;
	}

	/**
	 * 配置ShiroFilter
	 * 
	 * @param securityManager
	 * @return
	 */
	@Bean
	public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {

		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		// 拦截器.
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		
		// 配置静态资源不会被拦截的链接 顺序判断
		filterChainDefinitionMap.put("/bootstrap/**", "anon");
		filterChainDefinitionMap.put("/common/**", "anon");
		filterChainDefinitionMap.put("/layer/**", "anon");
		filterChainDefinitionMap.put("/webjars/**", "anon");
		
		// 获取验证码
		filterChainDefinitionMap.put("/getVerify", "anon");
		//登录
		filterChainDefinitionMap.put("/authorize", "anon");

		//配置记住我或认证通过可以访问的地址  
		filterChainDefinitionMap.put("/index", "user"); 
		
		// 配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
		filterChainDefinitionMap.put("/logout", "logout");
		// <!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
		// <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
		filterChainDefinitionMap.put("/**", "authc");
		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面

		shiroFilterFactoryBean.setLoginUrl("/login");
		// 登录成功后要跳转的链接
		shiroFilterFactoryBean.setSuccessUrl("/index");

		// 未授权界面;
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;

	}

	/**
	 * Shiro生命周期处理器 可以调用配置在 Spring IOC 容器中 shiro bean 的生命周期方法.
	 * 创建Bean的方法前面加上static。让使用configuration的类在没有实例化的时候不会去过早的要求@Autowired和@Value
	 * 进行注入。 不加static @Value获取不到值，发生不可预知的错误
	 */
	@Bean
	public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	/**
	 * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
	 * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
	 */
	@Bean
	@DependsOn({ "lifecycleBeanPostProcessor" })
	public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		advisorAutoProxyCreator.setProxyTargetClass(true);
		return advisorAutoProxyCreator;
	}

	/**
	 * AOP开启
	 * 
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
		return authorizationAttributeSourceAdvisor;
	}

	/**
	 * 注入SessionDAO
	 * 
	 * @return
	 */
	public SessionDAO redisSessionDao() {
		// 使用构造的方式
		ShiroRedisSessionDao sessionDao = new ShiroRedisSessionDao(sessionExpireTime, jedisPool);
		// 指定sessionid生成器
		sessionDao.setSessionIdGenerator(new JavaUuidSessionIdGenerator());
		return sessionDao;
	}

	/**
	 * 配置SessionManager
	 * 
	 * @return
	 */
	@Bean
	public SessionManager sessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setSessionDAO(redisSessionDao());
		// session过期时间
		sessionManager.setGlobalSessionTimeout(sessionExpireTime);
		// session过期删除
		sessionManager.setDeleteInvalidSessions(true);
		// 开启会话验证器
		sessionManager.setSessionValidationSchedulerEnabled(true);

		return sessionManager;
	}

	/**
	 * 注入CacheManager
	 * 
	 * @return
	 */
	@Bean
	public ShiroRedisCacheManager shiroRedisCacheManager() {
		return new ShiroRedisCacheManager(cacheExpireTime, jedisPool);
	}

	/**
	 * cookie对象; rememberMeCookie()方法是设置Cookie的生成模版，比如cookie的name，cookie的有效时间等等。
	 * @return
	 */
	@Bean
	public SimpleCookie rememberMeCookie() {
		// 这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
		SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
		// <!-- 记住我cookie生效时间30天 ,单位秒;-->
		simpleCookie.setMaxAge(259200);
		return simpleCookie;
	}

	/**
	 * cookie管理对象;
	 * rememberMeManager()方法是生成rememberMe管理器，而且要将这个rememberMe管理器设置到securityManager中
	 * @return
	 */
	@Bean
	public CookieRememberMeManager rememberMeManager() {
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCookie(rememberMeCookie());
		// rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
		cookieRememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
		return cookieRememberMeManager;
	}

	/**
	 * 注入SecurityManager
	 * shiro中主要部分
	 * @return
	 */
	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 1.设置自定义的Realm
		securityManager.setRealm(myShiroRealm());

		// 2.配置SessionManager
		securityManager.setSessionManager(sessionManager());
		// 3.配置CacheManager
		securityManager.setCacheManager(shiroRedisCacheManager());
		// 4.配置记住我管理器
		securityManager.setRememberMeManager(rememberMeManager());  
		return securityManager;
	}

}
