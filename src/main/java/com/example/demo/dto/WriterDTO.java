package com.example.demo.dto;

/**
 * @author ：11537qujiaqi
 * @date ：Created in 2020/5/18 15:06
 * @description： 测试懒加载
 * @modified By：
 * @version: 1$
 */
public class WriterDTO {
	private final String writerId;

	public WriterDTO(String writerId) {
		this.writerId = writerId;
		System.out.println(writerId + "初始化完毕");
	}

	public void write(String message) {
		System.out.println(writerId + ": " + message);
	}
}
