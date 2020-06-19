package com.example.demo.controller;

import com.example.demo.dto.Department;
import com.example.demo.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * @author ：11537qujiaqi
 * @date ：Created in 2020/5/18 10:32
 * @description：
 * @modified By：
 * @version: 1$
 */
@RestController
@Slf4j
@RequestMapping(value = "/dept")
public class DepartmentController implements Serializable {
	private static final long serialVersionUID = -657016590369292037L;
	@Resource
	DepartmentService departmentService;

	/**
	 * 级联查询
	 *
	 * @Slf4j log.info使用{}当占位符
	 */
	@GetMapping("/{id}")
	public Department selectDeptAndUser(@PathVariable("id") int id) {
		log.info("Processing trade with id: {} and code: {}", id, id);
		return departmentService.selectDeptAndUser(id);
	}
}
