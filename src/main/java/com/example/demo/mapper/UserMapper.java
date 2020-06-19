package com.example.demo.mapper;

import com.example.demo.dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ：11537qujiaqi
 * @date ：Created in 2020/4/23 17:02
 * @description：
 * @modified By：
 * @version: 1$
 * @Param 一般在mapper中使用，1方法有多个参数 2方法参数要取别名 3XML中的SQL使用了$ 4如果在动态SQL中使用了参数作为变量
 * 使用传入对象需要这样用的时候 #{对象.属性} 不加注解只能#{属性}
 */
@Mapper
public interface UserMapper {
	public int insert(User user);

	public int deleteUser(int userId);

	public int updateUser(User user);

	public User selectById(int userId);

	public List<User> selectAll();

	public int updateUserWithLock(User user);

	public void moveIn(@Param("userId") int id, @Param("money") BigDecimal money);

	public void moveOut(@Param("userId") int id, @Param("money") BigDecimal money);

	public List<User> selectUserByDept(int deptId);
}

