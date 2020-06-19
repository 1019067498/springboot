package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ：11537qujiaqi
 * @date ：Created in 2020/4/23 14:34
 * @description：
 * @modified By：
 * @version: 1$
 */
@Controller
public class LoginController {
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
}