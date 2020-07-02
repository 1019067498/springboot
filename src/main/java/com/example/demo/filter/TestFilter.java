package com.example.demo.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;

/**
 * @package: com.example.demo.filter
 * @author: QuJiaQi
 * @date: 2020/6/29 15:01
 */
@Slf4j
public class TestFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        log.info("TestFilter filter。。。。。。。。");
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
