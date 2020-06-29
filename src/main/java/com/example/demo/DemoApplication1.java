package com.example.demo;

import com.example.demo.dto.WriterDTO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

/**
 * @author Mickey
 * 测试懒加载
 */
@SpringBootApplication
public class DemoApplication1 {
	@Lazy(false)
	@Bean("writer1")
	public WriterDTO getWriter1() {
		return new WriterDTO("Writer 1");
	}

	@Lazy(false)
	@Bean("writer2")
	public WriterDTO getWriter2() {
		return new WriterDTO("Writer 2");
	}

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(DemoApplication1.class, args);
		System.out.println("Application context initialized!!!");

		WriterDTO writer1 = ctx.getBean("writer1", WriterDTO.class);
		writer1.write("writer1被调用");

		WriterDTO writer2 = ctx.getBean("writer2", WriterDTO.class);
		writer2.write("writer2被调用");
		/**
		 * 懒加载时
		 * Application context initialized!!!
		 * Writer 1初始化完毕
		 * Writer 1: writer1被调用
		 * Writer 2初始化完毕
		 * Writer 2: writer2被调用
		 * 非懒加载
		 * Writer 1初始化完毕
		 * Writer 2初始化完毕
		 * Application context initialized!!!
		 * Writer 1: writer1被调用
		 * Writer 2: writer2被调用
		 */
	}
}

