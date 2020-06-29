package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ：11537qujiaqi
 * @date ：Created in 2020/4/23 16:59
 * @description：
 * @modified By：
 * @version: 1$
 */
@Service
public interface UserService {
	public int addUser(UserDTO user);

	public int deleteUser(int userId);

	public int updateUser(UserDTO user);

	public UserDTO selectById(int userId);

	public List<UserDTO> selectAll();

	public int updateUserWithLock(UserDTO user);

	/**
	 * @param outer
	 * @param inner
	 * @param money
	 * @Transactional 注意参数propagation 事物传播行为 isolation 事物隔离级别
	 * 事务一般都在Services定义
	 */
	@Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
	public void transfer(int outer, int inner, BigDecimal money);
}
