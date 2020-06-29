package com.example.demo.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * @author ：11537qujiaqi
 * @date ：Created in 2020/4/23 16:57
 * @description：
 * @modified By：
 * @version: 1$
 * @Data 相当于@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode这5个注解的合集。
 * @EqualsAndHashCode (callSuper = true) @ToString(callSuper = true)
 * 若使用@Data需注意如果不手动加callSuper = true，那么判等和tostring时都不会有父类属性参与
 * @JsonInclude (value = JsonInclude.Include.NON_NULL) 属性值为null的不参与序列化
 * @TableName 实体类的类名和数据库表名不一致
 */
@Data
@Table(name = "t_user")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class UserDTO implements Serializable {
	private static final long serialVersionUID = 8675624927131711253L;
	/**
	 * redis保存对象的时候要求对象是序列化的且要有一个空的构造器,除非自定义redis序列化方式
	 * - 把对象转换为字节序列的过程称为对象的序列化。
	 * - 把字节序列恢复为对象的过程称为对象的反序列化。
	 * 对象的序列化主要有两种用途：
	 * 　　1） 把对象的字节序列永久地保存到硬盘上，通常存放在一个文件中；
	 * 　　2） 在网络上传送对象的字节序列。
	 * 显示地指定serialVersionUID是为了在序列化后修改了对象（不删除原有字段）时还能够反序列化回来。
	 * 如果不显示指定，在序列化后修改了对象那么serialVersionUID的值会变化，就无法匹配，导致反序列化失败
	 */
	@Id
	@KeySql(useGeneratedKeys = true)
	private Integer userId;
	private String userName;
	private String password;
	private String phone;
	/**
	 * @JsonFormat 是为了从数据库读取日期的格式化
	 * @DateTimeFormat 是为了向数据库写入日期的格式化
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateDate;
	private String myTest;
	private Integer version;
	private BigDecimal balance;
	private Integer deptId;


}

