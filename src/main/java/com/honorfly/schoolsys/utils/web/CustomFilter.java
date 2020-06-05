package com.honorfly.schoolsys.utils.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * 在SpringBoot中通过注解注册的方式简单的使用Filter
 * @author chengxi
 */
@Component
@ServletComponentScan
@WebFilter(urlPatterns = "/*", filterName = "myfilter")
public  class CustomFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("开始进行过滤处理");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {


        //调用该方法后，表示过滤器经过原来的url请求处理方法
        logger.info("---------------处理-------------");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("Filter销毁中");
    }
}