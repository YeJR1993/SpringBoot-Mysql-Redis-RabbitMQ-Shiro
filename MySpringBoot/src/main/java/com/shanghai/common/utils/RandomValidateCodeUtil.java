package com.shanghai.common.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shanghai.common.redis.JedisUtils;
import com.shanghai.common.utils.keyutils.SysModules;

/**
 * @author: YeJR
 * @version: 2018年5月18日 下午5:21:20 验证码工具类
 */
public class RandomValidateCodeUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(RandomValidateCodeUtil.class);
	
	/**
	 * 随机产生数字与字母组合的字符串
	 */
	private String randString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	/**
	 * 图片宽
	 */
	private int width = 95;
	
	/**
	 * 图片高
	 */
	private int height = 40;
	
	/**
	 * 干扰线数量
	 */
	private int lineSize = 40;
	
	/**
	 * 随机产生字符数量
	 */
	private int stringNum = 4;

	/**
	 * 边界值
	 */
	private int boundaryValue = 255;

	private Random random = new Random();

	/**
	 * 获得字体
	 */
	private Font getFont() {
		return new Font("Fixedsys", Font.CENTER_BASELINE, 24);
	}

	/**
	 * 获得颜色
	 */
	private Color getRandColor(int fc, int bc) {
		if (fc > boundaryValue) {
			fc = 255;
		}
		if (bc > boundaryValue) {
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc - 16);
		int g = fc + random.nextInt(bc - fc - 14);
		int b = fc + random.nextInt(bc - fc - 18);
		return new Color(r, g, b);
	}

	/**
	 * 生成随机图片
	 */
	public void getRandcode(HttpServletRequest request, HttpServletResponse response) {
		// BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		// 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
		Graphics g = image.getGraphics();
		// 图片大小
		g.fillRect(0, 0, width, height);
		// 字体大小
		g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 24));
		// 字体颜色
		g.setColor(getRandColor(110, 133));
		// 绘制干扰线
		for (int i = 0; i <= lineSize; i++) {
			drowLine(g);
		}
		// 绘制随机字符
		String randomString = "";
		for (int i = 1; i <= stringNum; i++) {
			randomString = drowString(g, randomString, i);
		}
		// 将生成的随机字符串保存到redis中
		Session session = SecurityUtils.getSubject().getSession();
		JedisUtils.set(SysModules.validateCode, session.getId().toString(), randomString);
		
		g.dispose();
		try {
			// 将内存中的图片通过流动形式输出到客户端
			ImageIO.write(image, "JPEG", response.getOutputStream());
		} catch (Exception e) {
			logger.info("将内存中的图片通过流动形式输出到客户端失败:{}", e);
		}

	}

	/**
	 * 绘制字符串
	 */
	private String drowString(Graphics g, String randomString, int i) {
		g.setFont(getFont());
		g.setColor(new Color(random.nextInt(101), random.nextInt(111), random.nextInt(121)));
		String rand = String.valueOf(getRandomString(random.nextInt(randString.length())));
		randomString += rand;
		g.translate(random.nextInt(3), random.nextInt(3));
		g.drawString(rand, 13 * i, 25);
		return randomString;
	}

	/**
	 * 绘制干扰线
	 */
	private void drowLine(Graphics g) {
		int x = random.nextInt(width);
		int y = random.nextInt(height);
		int xl = random.nextInt(13);
		int yl = random.nextInt(15);
		g.drawLine(x, y, x + xl, y + yl);
	}

	/**
	 * 获取随机的字符
	 */
	public String getRandomString(int num) {
		return String.valueOf(randString.charAt(num));
	}
	
}
