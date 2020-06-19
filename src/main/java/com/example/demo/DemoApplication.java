package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

/**
 * @author Mickey
 * 注意：@MapperScan("")这个注解非常的关键
 * @SpringBootApplication 会扫描当前包和所有同级包及子包
 * @EnableScheduling 注解启动定时任务
 * @ServletComponentScan 定义过滤器扫描的包
 * @Configuration 声明当前类是一个配置类，相当于一个Spring 配置的xml文件，包含于@SpringBootApplication中
 */
@SpringBootApplication
@MapperScan("com.example.demo.mapper")
@EnableScheduling
@EnableTransactionManagement
@ServletComponentScan("com.example.demo.mapper")
@Configuration
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	/**
	 * 文件上传配置
	 *
	 * @return
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		//  单个数据大小
		factory.setMaxFileSize(DataSize.ofGigabytes(20480));
		/// 总上传数据大小
		factory.setMaxRequestSize(DataSize.ofGigabytes(102400));
		return factory.createMultipartConfig();
	}


}
