package com.honorfly.schoolsys.utils.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;


public class MyURLFilter implements Filter{

    public void destroy() {
    }




    public void doFilter(ServletRequest req, ServletResponse res,FilterChain filterchan) throws IOException, ServletException {
      /*HttpServletRequest httpReq =(HttpServletRequest)req;
       String Url = httpReq.getRequestURI();
       if((Url.endsWith(".jsp")||Url.endsWith(".action"))&&!Url.endsWith("login.jsp")&&!Url.endsWith("login_1.jsp")&&!Url.endsWith("sys/user/userLogin.action")&&!Url.endsWith("/sys/user/validateCode.action")){
    	   HttpServletResponse httpRes =(HttpServletResponse)res;
    	   HttpSession session = httpReq.getSession();
    	   SysUser user=(SysUser)session.getAttribute(Constants.LOGIN_KEY);
    	   if(user==null){
    		   if(Url.endsWith(".jsp")){
    			   if(Url.contains("/new/")){
    				   httpRes.sendRedirect(httpReq.getContextPath()+"/new/login.jsp");
    			   }else{
    				   httpRes.sendRedirect(httpReq.getContextPath()+"/login.jsp");
    			   }
    		   }else{
    			   JSONHelper.returnInfo(httpRes,JSONHelper.failedInfo(Constants.no_session_code,Constants.no_session_msg));
    		   }
    	   }else{
    		   if(setting.getPermission().indexOf(Url)>-1){
    			   filterchan.doFilter(req, res);
    		   }else if(this.checkPermission(user, Url)){
    			   filterchan.doFilter(req, res);
    		   }else{
    			   JSONHelper.returnInfo(httpRes,JSONHelper.failedInfo(Constants.no_permission_code,Constants.no_permission_msg));
    		   }
    	   }
       }else{
           filterchan.doFilter(req, res);
       }*/
		filterchan.doFilter(req, res);
    	//filterchan.doFilter(req, res);
    }

    /**
     * 检查用记是否有权限
     * @Title:        checkPermission
     * @Description:  TODO
     * @param:        @param user
     * @param:        @param requestpath
     * @param:        @return
     * @return:       boolean
     * @throws
     * @author        Administrator
     * @Date          2015年10月27日 上午9:10:54
     */
   /* private boolean checkPermission(SysUser user , String requestpath){
    	boolean success = false;
    	first:for(SysRole role:user.getRoles()){
			   sencond:for(SysPermission permission:role.buttons){
				  System.out.println(requestpath+":---->"+permission.getPermissionUrl());
				  if((!StringUtils.isBlank(permission.getPermissionUrl())&&requestpath.endsWith(permission.getPermissionUrl()))||(!StringUtils.isBlank(permission.getNewPermissionUrl())&&requestpath.endsWith(permission.getNewPermissionUrl()))){
					  success = true;
					  break first;
				  }
			   }
		   }
    	return success;
    }*/

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

}
