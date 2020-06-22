package com.honorfly.schoolsys.utils.securiry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SelfAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    SelfUserDetailService selfUserDetailService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("----------------------");
        String username = (String)authentication.getPrincipal();
        String password = (String)authentication.getCredentials();
        /*if(1==1){
            new BadCredentialsException("账号密码错误！");
            return null;
        }*/
        List authentictates = new ArrayList();
        authentictates.add("/http://xxx");

        SelfUserDetail selfUserDetail = (SelfUserDetail) selfUserDetailService.loadUserByUsername(username);
         return  new UsernamePasswordAuthenticationToken(selfUserDetail,selfUserDetail.getPassword(),selfUserDetail.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true; //一定要true 才能调上authenticate
    }
}
