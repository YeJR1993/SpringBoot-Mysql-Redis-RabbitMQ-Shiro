package com.shanghai.modules.sys.web;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shanghai.common.rabbitmq.RabbitMqEnum;
import com.shanghai.common.rabbitmq.RabbitMqSender;
import com.shanghai.common.shiro.MyUsernamePasswordToken;
import com.shanghai.common.utils.CodeMsg;
import com.shanghai.common.utils.RandomValidateCodeUtil;
import com.shanghai.common.utils.SpringContextHolder;
import com.shanghai.common.utils.exception.LoginException;
import com.shanghai.modules.sys.entity.User;
import com.shanghai.modules.sys.service.UserService;

/**
 * @author YeJR
 * @version: 2018年5月28日 上午10:07:51 登录相关类
 */
@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	/**
	 * 服务器标识， 测试负载均衡使用
	 */
	private static final int Flag = 2;
	
	@Autowired
	private RabbitMqSender rabbitMqSender;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/login")
	public String login(Model model) {
		logger.info("开始登入：{}", Flag);
		//获取当前项目路径
		ServletContext context = SpringContextHolder.getBean(ServletContext.class);
		String baseUrl = context.getContextPath();
		model.addAttribute("baseUrl", baseUrl);
		// 当前用户信息
		Subject subject = SecurityUtils.getSubject();
		// 用户是授权或者记住我
		if (subject.isAuthenticated() || subject.isRemembered()) {
			return "redirect:/index";
		}
		return "modules/login";

	}

	/**
	 * 登录校验
	 * 
	 * @param username
	 * @param password
	 * @param rememberMe
	 * @return
	 */
	@RequestMapping(value = "/authorize")
	public String login(String username, String password, Boolean rememberMe, String validateCode) {
		logger.info("登入校验：{}", Flag);
		// 当前用户信息
		Subject subject = SecurityUtils.getSubject();
		// 用户是授权或者记住我
		if (subject.isAuthenticated() || subject.isRemembered()) {
			return "redirect:/index";
		}
		// 没有用户名密码直接回登录页
		if (username == null || password == null) {
			return "redirect:/login";
		}

		if (rememberMe == null) {
			rememberMe = false;
		}

		MyUsernamePasswordToken usernamePasswordToken = new MyUsernamePasswordToken(username, password, rememberMe, validateCode);
		// 进行验证，捕获异常
		try {
			subject.login(usernamePasswordToken);
		} catch (UnknownAccountException e) {
			throw new LoginException(CodeMsg.USER_NOT_EXIST);
		} catch (IncorrectCredentialsException e) {
			throw new LoginException(CodeMsg.NAME_NOT_MATCH_PASSWORD);
		} catch (LockedAccountException e) {
			throw new LoginException(CodeMsg.USER_LOCKED);
		} catch (AuthenticationException e) {
			CodeMsg.CODE_INVALID.setMsg(e.getMessage());
			throw new LoginException(CodeMsg.CODE_INVALID);
		}
		logger.info("校验通过：{}", Flag);
		return "redirect:/index";
	}

	/**
	 * 生成验证码
	 */
	@RequestMapping(value = "/getVerify")
	public void getVerify(HttpServletRequest request, HttpServletResponse response) {
		logger.info("生成验证码：{}", Flag);
		try {
			// 设置相应类型,告诉浏览器输出的内容为图片
			response.setContentType("image/jpeg");
			// 设置响应头信息，告诉浏览器不要缓存此内容
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expire", 0);
			RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
			// 输出验证码图片方法
			randomValidateCode.getRandcode(request, response);
		} catch (Exception e) {
			logger.info("获取验证码失败:{}", e);
		}
	}
	
	/**
	 * 首页
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String index() {
		logger.info("进入首页：{}", Flag);
		Subject subject = SecurityUtils.getSubject();
		String name = subject.getPrincipal().toString();
		Session session = subject.getSession();
		session.setAttribute("loginName", name);
		User user = userService.getByLoginName(name);
		rabbitMqSender.sendRabbitMqFanout(RabbitMqEnum.RoutingKeyEnum.SYSTEMTIPKEY.getCode(), user);
		return "modules/index";
	}

	/**
	 * 权限不足
	 * @return
	 */
	@RequestMapping(value = "/403")
	public String unAuthorize() {
		return "error/403";
	}
	
	/**
	 * 检查Excel导出是否结束
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/exportExcelFinished")
	public Boolean exportExcelFinished() {
		Session session = SecurityUtils.getSubject().getSession();
		Object falg =  session.getAttribute("exportExcel");
		if (falg != null) {
			if ((boolean) falg) {
				// 删除该session， 不然对下次导出造成影响
				session.removeAttribute("exportExcel");
				return true;
			}
		}
		return false;
	}
	

}
