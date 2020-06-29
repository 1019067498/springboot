package com.example.demo.service.impl;

import com.example.demo.dto.UserDTO;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author ：11537qujiaqi
 * @date ：Created in 2020/5/7 14:28
 * @description：
 * @modified By：
 * @version: 1$
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;

	@Override
	public int addUser(UserDTO user) {

		return userMapper.insert(user);
	}

	@Override
	public List<UserDTO> selectAll() {
		return userMapper.selectAll();
	}

	@Override
	public UserDTO selectById(int userId) {
		return userMapper.selectById(userId);
	}

	@Override
	public int deleteUser(int userId) {
		return userMapper.deleteUser(userId);
	}

	@Override
	public int updateUser(UserDTO user) {
		return userMapper.updateUser(user);
	}

	@Override
	public int updateUserWithLock(UserDTO user) {
		return userMapper.updateUserWithLock(user);
	}

	@Override
	public void transfer(int outer, int inner, BigDecimal money) {
		userMapper.moveOut(outer, money);
		// 抛出异常
		int i = 1 / 0;
		userMapper.moveIn(inner, money);
	}
}

