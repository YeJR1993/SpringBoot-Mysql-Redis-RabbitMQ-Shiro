package com.shanghai.modules.sys.web;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.shanghai.common.utils.DateTimeUtils;
import com.shanghai.common.utils.constant.TimeConstants;

/**
* @author: YeJR 
* @version: 2018年7月24日 上午9:18:33
* 文件上传
*/
@Controller
@RequestMapping(value = "webuploader")
public class UploadController {
	
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
	
	
	/**
	 * 文件上传路径
	 */
	@Value("${upload.filePath}")
	private String filePath;
	
	/**
	 * 打开上传页面
	 * @param upType 上传类型
	 * @param objId 上传控件ID
	 * @param upNum 上传数量
	 * @param parentName 父窗口名称
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "openUploadPage")
	public String openUploadPage(String upType, String objId, Integer upNum,String parentName, Model model) {
		model.addAttribute("parentName", parentName);
		model.addAttribute("upType", upType);
		model.addAttribute("objId", objId);
		model.addAttribute("upNum", upNum);
		return "modules/global/upload";
	}
	
	/**
	 * 上传文件
	 * @RequestParam适用于name-valueString类型的请求域，@RequestPart适用于复杂的请求域（像JSON，XML）
	 * @param file
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@ResponseBody
	@RequestMapping(value = "upload")
	public String upload(@RequestPart("file") MultipartFile file) throws IllegalStateException, IOException {
		// 路径分隔符
		String separator = File.separator;
		
		// 获取文件名
		String fileName = file.getOriginalFilename();
		logger.info("上传文件：{}", fileName);
		
		// 获取文件的后缀名
		String suffixName = fileName.substring(fileName.lastIndexOf("."));
		
		// 解决中文问题，liunx下中文路径，图片显示问题, 使用uuid替换文件名称
		fileName = UUID.randomUUID().toString().replace("-", "") + suffixName;

		// 文件按上传日期分开
		File dest = new File(filePath + separator + DateTimeUtils.getServerTime(TimeConstants.YYYYMMDD) + separator + fileName);

		// 检测是否存在目录
		if (!dest.getParentFile().exists()) {
			dest.getParentFile().mkdirs();
		}
		file.transferTo(dest);
		return dest.getPath();
	}
	
}
