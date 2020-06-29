package com.example.demo.service;

import com.example.demo.dto.DepartmentDTO;
import org.springframework.stereotype.Service;

/**
 * @author Mickey
 */
@Service
public interface DepartmentService {
	public DepartmentDTO selectDeptAndUser(int id);
}
