

package com.honorfly.schoolsys.controller;


import com.honorfly.schoolsys.entry.TeachCourseType;
import com.honorfly.schoolsys.utils.web.BaseController;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/teachCourseType/")
public class TeachCourseTypeAction extends BaseController {
    @Bean
    public TeachCourseType setTeachCourseType() throws BeansException {
        entityObjClazz = TeachCourseType.class;
        return  null;
    }

}
