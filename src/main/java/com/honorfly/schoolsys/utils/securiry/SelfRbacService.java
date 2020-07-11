package com.honorfly.schoolsys.utils.securiry;

import com.honorfly.schoolsys.entry.SessionUser;
import com.honorfly.schoolsys.utils.AppConst;
import com.honorfly.schoolsys.utils.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    RedisUtil redisUtil;

    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object obj = authentication.getPrincipal();
        if (obj instanceof UserDetails) {
            if(AppWhiteList.needLoginWhiteList.contains(request.getRequestURI())){
                return true;
            }

            if(AppWhiteList.notNeedLoginWhiteList.contains(request.getRequestURI())){
                return true;
            }

            SessionUser sessionUser = (SessionUser) obj;
            SessionUser redisUser = (SessionUser) redisUtil.get(AppConst.Redis_Session_Namespace+sessionUser.getId());
            if(redisUser.buttonsUrls.contains(request.getRequestURI())){
                return true;
            }
            //return false;
            return true;
        } else {
            //没有登录
            return false;
        }
    }
}
