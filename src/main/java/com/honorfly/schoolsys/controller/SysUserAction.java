

package com.honorfly.schoolsys.controller;


import com.honorfly.schoolsys.entry.SysUser;
import com.honorfly.schoolsys.utils.web.BaseController;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/sysUser/")
public class SysUserAction extends BaseController {
    @Bean
    public SysUser setSysUser() throws BeansException {
        entityObjClazz = SysUser.class;
        return  null;
    }

}
