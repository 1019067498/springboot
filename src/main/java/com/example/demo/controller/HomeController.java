package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：11537qujiaqi
 * @date ：Created in 2020/4/23 14:28
 * @description：
 * @modified By：
 * @version: 1$
 */

/**
 * RestController和Controller注解的区别是：
 * RestController是返回的内容就是返回的内容，相当于加个@ResponseBody,而controller一般是返回的页面
 *
 * @author Mickey
 */
@RestController
public class HomeController {
	/**
	 * 获取配置文件中的值
	 */
	@Value("${mybatis.mapper-locations}")
	private String property;

	@RequestMapping("/hello")
	public String home() {
		return "Hello, SpringBoot!  " + property;
	}
}
