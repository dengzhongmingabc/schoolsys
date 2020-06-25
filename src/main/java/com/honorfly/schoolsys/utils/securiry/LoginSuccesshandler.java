package com.honorfly.schoolsys.utils.securiry;

import com.alibaba.fastjson.JSON;
import com.honorfly.schoolsys.entry.SessionUser;
import com.honorfly.schoolsys.service.ISysPermissionService;
import com.honorfly.schoolsys.utils.AppConfig;
import com.honorfly.schoolsys.utils.AppConst;
import com.honorfly.schoolsys.utils.JWT;
import com.honorfly.schoolsys.utils.ResultGenerator;
import com.honorfly.schoolsys.utils.redis.RedisUtil;
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
    private ISysPermissionService sysPermissionService;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    AppConfig appConfig;

    @Autowired
    JWT jwt;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
        SessionUser sessionUser = (SessionUser) authentication.getPrincipal();
        redisUtil.set(AppConst.Redis_Session_Namespace +sessionUser.getId(),sessionUser, appConfig.getSessionExpire());
        String token = jwt.generateToken(sessionUser.getId());
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(JSON.toJSONString(ResultGenerator.genSuccessResult("Bearer "+token)));
    }
}
