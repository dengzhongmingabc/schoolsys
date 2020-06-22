package com.honorfly.schoolsys.utils.securiry;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SelfUserDetailService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SelfUserDetail selfUserDetail = new SelfUserDetail();
        selfUserDetail.setPassword(new BCryptPasswordEncoder().encode("123456"));
        selfUserDetail.setUsername("username");
        /*List authentictates = new ArrayList();
        authentictates.add("/http://xxx");
        selfUserDetail.getAuthorities().addAll(authentictates);*/
        return selfUserDetail;
    }
}
