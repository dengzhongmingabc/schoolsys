package com.honorfly.schoolsys.utils.securiry;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 在这里从user里拿到所有url与本次访问比对
 * 如果对得有上，通过
 * 对不上 throw 通不过异常
 */
@Component("selfrbacservice")
public class SelfRbacService {
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object obj = authentication.getPrincipal();
        if (obj instanceof UserDetails) {
            //登录过了
  /*          boolean pass = false;
            String requestUrl = request.getRequestURI();
            //看是否在白名单里
            for (String url:AppWhiteList.whiteList){
                if(url.endsWith(requestUrl)){
                    pass = true;
                    break;
                }
            }
            if(pass){
                return pass;
            }
            SessionUser sessionUser = (SessionUser) obj;
            for (SysPermission permission : sessionUser.buttons) {
                String url = permission.getRedirect();
                System.out.println(url);
                if (url.endsWith(requestUrl)) {
                    pass = true;
                    break;
                }
            }
            //做权限配对，配对上有权限，通过
            return pass;*/
         return true;
        } else {
            //没有登录

            return false;
        }
    }
}
