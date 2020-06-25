package com.honorfly.schoolsys.utils.securiry;

import com.alibaba.fastjson.JSON;
import com.honorfly.schoolsys.utils.ResultGenerator;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginFailHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException exception) throws IOException, ServletException {
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(JSON.toJSONString(ResultGenerator.genFailResult("账号密码不正确。请重试！")));
    }
}
