package com.honorfly.schoolsys.utils.securiry;

import com.alibaba.fastjson.JSON;
import com.honorfly.schoolsys.entry.SessionUser;
import com.honorfly.schoolsys.service.impl.SysUserServiceImpl;
import com.honorfly.schoolsys.utils.JWT;
import com.honorfly.schoolsys.utils.ResultGenerator;
import com.honorfly.schoolsys.utils.redis.RedisUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private final String tokenHeader="Access-Token";

    @Autowired
    JWT jwt;
    @Autowired
    SelfUserDetailService selfUserDetailService;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    SysUserServiceImpl sysUserService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String requestHeader = request.getHeader(this.tokenHeader);
        System.out.println("url:"+request.getRequestURI()+"    \ntoken:"+requestHeader);
        Claims claim = null;
        String authToken = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring("Bearer ".length());
            try {
                claim =  jwt.getClaimByToken(authToken);
            } catch (ExpiredJwtException e) {
            }
        }
        if(!StringUtils.isBlank(authToken)&&claim == null){
            //过期
            response.getWriter().write(JSON.toJSONString(ResultGenerator.genFailResult("Need login")));
            return;
        }
        if (claim != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            SessionUser sessionUser = (SessionUser) redisUtil.get("session"+claim.getSubject());
            /*SysUser user = sysUserService.getById(SysUser.class,Long.valueOf(claim.getSubject()));

            SessionUser sessionUser = new SessionUser();
            BeanUtils.copyProperties(user,sessionUser);
            sessionUser.setUsername(user.getUserName());
            for(SysRole role:user.getRoles()){
                if(role.getPermissions()!=null&&role.getPermissions().size()>0){
                    sessionUser.buttons.addAll(role.getPermissions());
                }
            }*/
           UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(sessionUser, null, sessionUser.getAuthorities());
           SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
