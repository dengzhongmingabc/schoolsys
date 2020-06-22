package com.honorfly.schoolsys.utils.securiry;

import com.honorfly.schoolsys.utils.JWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private final String tokenHeader="Authority";

    @Autowired
    JWT jwt;
    @Autowired
    SelfUserDetailService selfUserDetailService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String requestHeader = request.getParameter(this.tokenHeader);
        Claims claim = null;
        String authToken = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring("Bearer ".length());
            try {
                claim =  jwt.getClaimByToken(authToken);
            } catch (ExpiredJwtException e) {
            }
        }

        if(authToken!=null&&claim == null){
            //过期
        }
        UserDetails userDetails = selfUserDetailService.loadUserByUsername(claim.getSubject());
        if(userDetails==null){
            //没有对应的用户，
        }
        if (claim != null && SecurityContextHolder.getContext().getAuthentication() == null) {
           UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
           SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
