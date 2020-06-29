package com.example.demo.config;

import com.example.demo.filter.TestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * @package: com.example.demo.config
 * @author: QuJiaQi
 * @date: 2020/6/29 15:03
 */
@Configuration
public class FilterConfig {
    @Bean
    Filter testFilter() {
        return new TestFilter();
    }

    @Bean
    public FilterRegistrationBean<TestFilter> filterRegistrationBean1() {
        FilterRegistrationBean<TestFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter((TestFilter) testFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        //filterRegistrationBean.setOrder();多个filter的时候order的数值越小 则优先级越高
        return filterRegistrationBean;
    }

}
