package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * @author ：11537qujiaqi
 * @date ：Created in 2020/5/15 17:17
 * 懒加载与非懒加载 ：bean的实例化是发生在IoC容器启动时还是在第一次被用到时，具体示例见DemoApplication1
 * @JsonIgnoreProperties 级联查询是默认为懒加载, 在懒加载且返回json格式的前提下，调用service方法返回实体类会被做代理而多出来一个handler属性
 * 之后Jackson在对该代理类做序列化时,由于找不到对应的getter,就会抛异常，所以用到让Jackson序列化时忽略handler属性
 * @transient 关键字为我们提供了便利，你只需要实现Serializable接口，将不需要序列化的属性前添加关键字transient，
 * 序列化对象的时候，这个属性就不会序列化到指定的目的地中
 */
@Data
@Table(name = "department")
@JsonIgnoreProperties(value = {"handler"})
public class Department implements Serializable {
	private static final long serialVersionUID = -8067003343072569597L;
	@Id
	@KeySql(useGeneratedKeys = true)
	private Integer id;
	private String deptName;
	@Transient
	private List<User> users;

	@Override
	public String toString() {
		return "Department{" +
				"id=" + id +
				", deptName='" + deptName + '\'' +
				", users=" + users +
				'}';
	}
}
