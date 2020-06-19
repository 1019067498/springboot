package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Author jiaqi.qu@hand-china.com 2020/5/27 13:39
 * @Version 1.0
 */
@Controller
public class FileUploadController {
	/**
	 * 获取file.html页面
	 */
	@RequestMapping("file")
	public String file() {
		return "/file";
	}

	/**
	 * 实现文件上传 SpringBoot的默认上传文件的大小是1M 如果你上传的文件超过了1M就会出现这样的错误
	 * org.apache.tomcat.util.http.fileupload.FileUploadBase$FileSizeLimitExceededException: The field
	 * fileName exceeds its maximum permitted size of 1048576 bytes.
	 */
	@RequestMapping("fileUpload")
	@ResponseBody
	public String fileUpload(@RequestParam("fileName") MultipartFile file) {
		if (file.isEmpty()) {
			return "请添加一个文件";
		}
		String fileName = file.getOriginalFilename();
		int size = (int) file.getSize();
		System.out.println(fileName + "-->" + size);

		String path = "F:/test";
		File dest = new File(path + "/" + fileName);
		boolean flag = dest.exists();
		while (dest.exists()) {
			// 文件存在时重命名
			int i = 1;
			String[] fileNameArray = fileName.split("\\.");
			fileName = fileNameArray[0] + i++ + "." + fileNameArray[1];
			dest = new File(path + "/" + fileName);
		}
		if (!dest.getParentFile().exists()) {
			// 判断文件父目录是否存在
			dest.getParentFile().mkdir();
		}
		try {
			file.transferTo(dest);
			// 保存文件
			return "上传成功";
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return "上传失败";
		} catch (IOException e) {
			e.printStackTrace();
			return "上传失败";
		}
	}

	/**
	 * 获取multifile.html页面
	 *
	 * @return
	 */
	@RequestMapping("multifile")
	public String multifile() {
		return "/multifile";
	}

	/**
	 * 实现多文件上传
	 */
	@RequestMapping(value = "multifileUpload", method = RequestMethod.POST)
	@ResponseBody
	public String multifileUpload(HttpServletRequest request) {

		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("fileName");

		if (files.isEmpty()) {
			return "请添加至少一个文件";
		}

		String path = "F:/test";

		for (MultipartFile file : files) {
			String fileName = file.getOriginalFilename();
			int size = (int) file.getSize();
			System.out.println(fileName + "-->" + size);

			// if(file.isEmpty()){
			// return "上传失败";
			// }else{
			File dest = new File(path + "/" + fileName);
			while (dest.exists()) {
				// 文件存在时重命名
				int i = 1;
				String[] fileNameArray = fileName.split("\\.");
				fileName = fileNameArray[0] + i++ + "." + fileNameArray[1];
				dest = new File(path + "/" + fileName);
			}

			if (!dest.getParentFile().exists()) {
				// 判断文件父目录是否存在
				dest.getParentFile().mkdir();
			}

			try {
				file.transferTo(dest);
			} catch (Exception e) {
				e.printStackTrace();
				return "上传失败";
			}
			// }
		}
		return "上传成功";
	}
}
