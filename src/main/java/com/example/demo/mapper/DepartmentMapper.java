package com.example.demo.mapper;

import com.example.demo.dto.Department;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ：11537qujiaqi
 * @date ：Created in 2020/5/15 17:29
 * @description： 一对多的级联查询跟一对一级联查询一样也不能双向关联，否则同样会报java.lang.StackOverflowError错误。
 * @modified By：
 * @version: 1$
 */
@Mapper
public interface DepartmentMapper {
	public Department selectDeptAndUser(int id);
}
