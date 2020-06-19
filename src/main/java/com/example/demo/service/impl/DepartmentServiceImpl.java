package com.example.demo.service.impl;

import com.example.demo.dto.Department;
import com.example.demo.mapper.DepartmentMapper;
import com.example.demo.service.DepartmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ：11537qujiaqi
 * @date ：Created in 2020/5/15 17:27
 * @description：
 * @modified By：
 * @version: 1$
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {
	@Resource
	private DepartmentMapper departmentMapper;

	/**
	 * @param id
	 * @return
	 */
	@Override
	public Department selectDeptAndUser(int id) {
		return departmentMapper.selectDeptAndUser(id);
	}
}
