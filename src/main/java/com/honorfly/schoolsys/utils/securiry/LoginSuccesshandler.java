package com.honorfly.schoolsys.utils.securiry;

import com.alibaba.fastjson.JSON;
import com.honorfly.schoolsys.entry.SessionUser;
import com.honorfly.schoolsys.entry.SysRole;
import com.honorfly.schoolsys.entry.SysUser;
import com.honorfly.schoolsys.service.ISysPermissionService;
import com.honorfly.schoolsys.utils.AppConfig;
import com.honorfly.schoolsys.utils.AppConst;
import com.honorfly.schoolsys.utils.JWT;
import com.honorfly.schoolsys.utils.ResultGenerator;
import com.honorfly.schoolsys.utils.redis.RedisUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        SysUser user = null;
        if (sessionUser.getId() > 1) {//密码账号认证
            try {
                user = sysPermissionService.getById(SysUser.class, sessionUser.getId());
            } catch (Exception e) {
                throw new UsernameNotFoundException("没有对应的用户");
            }
            if (user == null) {
                throw new UsernameNotFoundException("没有对应的用户");
            }
        } else {//手机验证码认证
            try {
                user = sysPermissionService.loadUserByMobile(sessionUser.getUsername());
            } catch (Exception e) {
                throw new UsernameNotFoundException("没有对应的用户");
            }
            if (user == null) {
                user = new SysUser();
                user.setMobile(sessionUser.getUsername());
                user.setUserName(sessionUser.getUsername());
                user.setRealName("试用老师：" + sessionUser.getUsername());
                user.setPassword(new BCryptPasswordEncoder().encode(RandomStringUtils.randomAlphanumeric(10)));//随机随机生成密码
                SysRole sysRole = sysPermissionService.getById(SysRole.class, 14L);//给一个默认的角色 14：老师
                user.getRoles().add(sysRole);
                sysPermissionService.update(user);
                try {
                    user = sysPermissionService.loadUserByMobile(sessionUser.getUsername());
                } catch (Exception e) {
                    throw new UsernameNotFoundException("没有对应的用户");
                }
            }
        }
        SessionUser redisUser = new SessionUser();
        redisUser.setUsername(user.getUserName());
        redisUser.setPassword(user.getPassword());
        redisUser.setId(user.getId());
        redisUser.setRealName(user.getRealName());
        redisUser.setParentId(user.getParentId());
        for (SysRole role : user.getRoles()) {
            if (role.getPermissions() != null && role.getPermissions().size() > 0) {
                redisUser.buttons.addAll(role.getPermissions());
            }
        }
        redisUtil.set(AppConst.Redis_Session_Namespace + redisUser.getId(), redisUser, appConfig.getSessionExpire());
        String token = jwt.generateToken(redisUser.getId());
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(JSON.toJSONString(ResultGenerator.genSuccessResult("Bearer " + token)));
    }
}
