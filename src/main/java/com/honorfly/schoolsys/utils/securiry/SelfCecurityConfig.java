package com.honorfly.schoolsys.utils.securiry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
public class SelfCecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    SelfAuthenticationProvider selfAuthenticationProvider;
    @Autowired
    SelfAuthenticationEntryPoint selfAuthenticationEntryPoint;

    @Autowired
    MyAccessDeniedHandler myAccessDeniedHandler;


    @Autowired
    SelfUserDetailService selfUserDetailService;

    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    LoginSuccesshandler loginSuccesshandler;

    @Autowired
    LoginFailHandler loginFailHandler;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(selfAuthenticationProvider);
        auth.userDetailsService(selfUserDetailService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
               /* .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//用jwt token后 将原来的session 改成无状态
                .and()*/
                .httpBasic().authenticationEntryPoint(selfAuthenticationEntryPoint) //设置无权限处理器
                .and()
                //.authenticated()
                .authorizeRequests().anyRequest().access("@selfrbacservice.hasPermission(request, authentication)")//设置权限过虑器
                .and()
                .formLogin().loginPage("/login").usernameParameter("username").passwordParameter("password")
                .successHandler(loginSuccesshandler)
                .failureHandler(loginFailHandler).permitAll()//和表单登录相关的接口统统都直接通过
                .and()
                .logout().logoutUrl("/logout")
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter out = resp.getWriter();
                        out.write("logout success");
                        out.flush();
                    }
                })
                .and()
                .csrf().disable();

                http.exceptionHandling().accessDeniedHandler(myAccessDeniedHandler);
                http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

    }
}
