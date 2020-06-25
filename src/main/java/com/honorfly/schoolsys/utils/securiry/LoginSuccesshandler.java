package com.honorfly.schoolsys.utils.securiry;

import com.alibaba.fastjson.JSON;
import com.honorfly.schoolsys.entry.SessionUser;
import com.honorfly.schoolsys.utils.JWT;
import com.honorfly.schoolsys.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccesshandler implements AuthenticationSuccessHandler {
    @Autowired
    JWT jwt;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
        resp.setContentType("application/json;charset=utf-8");


       // SecurityContextHolder.getContext().setAuthentication(authentication);


        SessionUser obj = (SessionUser) authentication.getPrincipal();
        String token = jwt.generateToken(obj.getId());
        resp.getWriter().write(JSON.toJSONString(ResultGenerator.genSuccessResult("Bearer "+token)));
    }
}
