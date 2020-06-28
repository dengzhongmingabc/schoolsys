package com.honorfly.schoolsys.utils.securiry;

import com.honorfly.schoolsys.entry.SessionUser;
import com.honorfly.schoolsys.entry.SysUser;
import com.honorfly.schoolsys.service.ISysPermissionService;
import com.honorfly.schoolsys.utils.AppConfig;
import com.honorfly.schoolsys.utils.AppConst;
import com.honorfly.schoolsys.utils.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SelfUserDetailService implements UserDetailsService {
    @Autowired
    private ISysPermissionService sysPermissionService;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    AppConfig appConfig;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //先验证码验证
        if(s.contains("mobile:")){
            String mobile = s.substring("mobile:".length());
            Object obj = redisUtil.get(AppConst.Redis_Captcha_Namespace+mobile);
            if(obj==null){
                throw new UsernameNotFoundException("验证码已过期或者不正确,请重新获取.");
            }
            SessionUser sessionUser = new SessionUser();
            sessionUser.setUsername(mobile);
            sessionUser.setPassword(new BCryptPasswordEncoder().encode((String)obj));
            return sessionUser;
        }
        //账号密码验证
        SysUser user = null;
        try {
            user = sysPermissionService.loadUser(s);
        }catch (Exception e){
          throw new UsernameNotFoundException("没有对应的用户");
        }
        if(user==null){
            throw new UsernameNotFoundException("没有对应的用户");
        }
        SessionUser sessionUser = new SessionUser();
        sessionUser.setUsername(user.getUserName());
        sessionUser.setPassword(user.getPassword());
        sessionUser.setId(user.getId());
        sessionUser.setRealName(user.getRealName());
        sessionUser.setParentId(user.getParentId());
        return sessionUser;
    }
}
