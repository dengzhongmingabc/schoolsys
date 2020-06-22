package com.honorfly.schoolsys.utils.securiry;

import com.alibaba.fastjson.JSON;
import com.honorfly.schoolsys.utils.ResultGenerator;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
     * @Author: Galen
     * @Date: 2019/3/27-17:36
     * @Description: Denied是拒签的意思
     * 此处我们可以自定义403响应的内容,让他返回我们的错误逻辑提示
     **/
@Component
    public class MyAccessDeniedHandler implements AccessDeniedHandler {
        @Override
        public void handle(HttpServletRequest httpServletRequest, HttpServletResponse resp,AccessDeniedException e) throws IOException {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            resp.setContentType("application/json;charset=UTF-8");
            resp.getWriter().write(JSON.toJSONString(ResultGenerator.genFailResult("权限不足，请联系管理员!")));
        }
    }
