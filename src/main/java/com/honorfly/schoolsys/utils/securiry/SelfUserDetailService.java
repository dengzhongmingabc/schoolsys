package com.honorfly.schoolsys.utils.securiry;

import com.honorfly.schoolsys.entry.SessionUser;
import com.honorfly.schoolsys.entry.SysRole;
import com.honorfly.schoolsys.entry.SysUser;
import com.honorfly.schoolsys.service.ISysPermissionService;
import com.honorfly.schoolsys.utils.AppConfig;
import com.honorfly.schoolsys.utils.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        for(SysRole role:user.getRoles()){
            if(role.getPermissions()!=null&&role.getPermissions().size()>0){
                sessionUser.buttons.addAll(role.getPermissions());
            }
        }
        return sessionUser;
    }
}
