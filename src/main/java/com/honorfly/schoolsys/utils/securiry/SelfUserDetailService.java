package com.honorfly.schoolsys.utils.securiry;

import com.honorfly.schoolsys.entry.SessionUser;
import com.honorfly.schoolsys.entry.SysRole;
import com.honorfly.schoolsys.entry.SysUser;
import com.honorfly.schoolsys.service.ISysPermissionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class SelfUserDetailService implements UserDetailsService {
    @Autowired
    private ISysPermissionService sysPermissionService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
 /*       SelfUserDetail selfUserDetail = new SelfUserDetail();
        selfUserDetail.setPassword(new BCryptPasswordEncoder().encode("123456"));
        selfUserDetail.setUsername("username");*/
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
        BeanUtils.copyProperties(user,sessionUser);
        sessionUser.setUsername(user.getUserName());
        for(SysRole role:user.getRoles()){
            if(role.getPermissions()!=null&&role.getPermissions().size()>0){
                sessionUser.buttons.addAll(role.getPermissions());
            }
        }
        return sessionUser;
    }
}
