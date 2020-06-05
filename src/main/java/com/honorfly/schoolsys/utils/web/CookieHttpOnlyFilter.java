package com.honorfly.schoolsys.utils.web;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 解决检测到会话cookie中缺少HttpOnly属性的问题
 * @version 1.0
 * @project： jshop
 * @author：QC班长

 * @date：2017/6/10
 * @time：10:53
 */
public class CookieHttpOnlyFilter implements Filter {
    public void destroy() {

    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {
        // TODO Auto-generated method stub
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String agent = req.getHeader("User-Agent");
		if(agent.contains("Dalvik")){
			filterChain.doFilter(request, response);
		}else{
			 Cookie[] cookies = req.getCookies();
		        if(cookies!=null){
		            for(Cookie cookie : cookies){
		                String value = cookie.getValue();
		                StringBuilder builder = new StringBuilder();
		                builder.append("JSESSIONID=" + value + "; ");
		                builder.append("Secure; ");
		                builder.append("HttpOnly; ");
		                Calendar cal = Calendar.getInstance();
		                cal.add(Calendar.HOUR, 1);
		                Date date = cal.getTime();
		                Locale locale = Locale.CHINA;
		                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss",locale);
		                builder.append("Expires=" + sdf.format(date));
		                resp.setHeader("Set-Cookie", builder.toString());
		            }
		        }
		        filterChain.doFilter(request, response);
		}
    }

    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub

    }
}
