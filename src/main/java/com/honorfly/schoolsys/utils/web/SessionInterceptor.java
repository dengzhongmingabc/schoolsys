package com.honorfly.schoolsys.utils.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
public class SessionInterceptor implements HandlerInterceptor
{
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
 /*       StringBuffer sb = new StringBuffer();
        Enumeration<String> keys = request.getParameterNames();
        while(keys.hasMoreElements()) {
            String k = keys.nextElement();
            sb.append("\n"+k + " = " + request.getParameter(k) );
        }
        logger.info("\nuri:"+request.getRequestURI()+" \n参数："+sb.toString());*/
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {



    }
}
