package com.honorfly.schoolsys.utils.securiry;

import com.alibaba.fastjson.JSON;
import com.honorfly.schoolsys.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    CORSFilter corsFilter;


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

    /**
     * @Author: Galen
     * @Description: 配置放行的资源
     * @Date: 2019/3/28-9:23
     * @Param: [web]
     * @return: void
     **/
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/index.html", "/static/**", "/login_p", "/favicon.ico")
                .mvcMatchers("/sys/user/getCaptcha")
                .mvcMatchers("/wechatAction/**")
                // 给 swagger 放行；不需要权限能访问的资源
                .antMatchers("/swagger-ui.html", "/swagger-resources/**", "/images/**", "/webjars/**", "/v2/api-docs", "/configuration/ui", "/configuration/security");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//用jwt token后 将原来的session 改成无状态
                .and()
                .httpBasic().authenticationEntryPoint(selfAuthenticationEntryPoint) //设置无权限处理器
                .and()
                //.authenticated()
                .authorizeRequests().anyRequest().access("@selfrbacservice.hasPermission(request, authentication)")//设置权限过虑器
                .and()
                .formLogin().loginPage("/login").loginProcessingUrl("/login")
                .successHandler(loginSuccesshandler)
                .failureHandler(loginFailHandler).permitAll()//和表单登录相关的接口统统都直接通过
                .and()
                .logout().logoutUrl("/logout")
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=utf-8");
                        resp.getWriter().write(JSON.toJSONString(ResultGenerator.genSuccessResult("注销成功")));
                    }
                })
                .and()
                .csrf().disable();
                http.exceptionHandling().accessDeniedHandler(myAccessDeniedHandler);
                http.addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class);
                http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);


    }


}
