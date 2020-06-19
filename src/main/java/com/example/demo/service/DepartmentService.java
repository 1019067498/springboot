package com.example.demo.service;

import com.example.demo.dto.Department;
import org.springframework.stereotype.Service;

/**
 * @author Mickey
 */
@Service
public interface DepartmentService {
	public Department selectDeptAndUser(int id);
}
