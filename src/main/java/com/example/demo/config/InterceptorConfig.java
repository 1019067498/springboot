package com.example.demo.config;

import com.example.demo.interceptor.TestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @package: com.example.demo.config
 * @author: QuJiaQi
 * @date: 2020/6/29 15:09
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Resource
    TestInterceptor testInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(testInterceptor)
                .addPathPatterns("/user/**");
    }

}
