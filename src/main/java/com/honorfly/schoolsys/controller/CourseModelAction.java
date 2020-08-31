

package com.honorfly.schoolsys.controller;


import com.honorfly.schoolsys.entry.CourseModel;
import com.honorfly.schoolsys.entry.TeachStudentTeacher;
import com.honorfly.schoolsys.service.ITeachService;
import com.honorfly.schoolsys.utils.Result;
import com.honorfly.schoolsys.utils.ResultGenerator;
import com.honorfly.schoolsys.utils.web.BaseController;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/courseModel/")
public class CourseModelAction extends BaseController {
    @Autowired
    ITeachService teachService;

    @Bean
    public CourseModel setCourseModel() throws BeansException {
        entityObjClazz = CourseModel.class;
        return null;
    }

    @ApiOperation(value = "新增")
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestBody Map<String, Object> args) throws Exception {
        CourseModel obj = (CourseModel) entityObjClazz.newInstance();
        for (Map.Entry<String, Object> entry : args.entrySet()) {
            obj.setPropertyValue(entry.getKey(), entry.getValue());
        }
        TeachStudentTeacher teachStudentTeacher = baseService.getById(TeachStudentTeacher.class,obj.getId());

        obj.setId(null);
        List weekModel = (ArrayList) args.get("weekModel");

        List events = (ArrayList) args.get("events");
        Map classOrOneByOne = (LinkedHashMap) args.get("classOrOneByOne");
        Map teacher = (LinkedHashMap) args.get("teacher");
        obj.setEvents(events);
        obj.setWeekModel(weekModel);
        obj.setClassId(Long.valueOf(classOrOneByOne.get("key").toString()));
        obj.setClassName(classOrOneByOne.get("label").toString());

        obj.setTeacherId(Long.valueOf(teacher.get("key").toString()));
        obj.setTeacherName(teacher.get("label").toString());


        //保存之前 先删除旧的排课方式
        teachService.deleteCourseModel(obj.getClassId());

        baseService.save(obj);

        //修改老师ID
        teachStudentTeacher.setTeacherId(obj.getTeacherId());
        teachStudentTeacher.setCourseModel(obj);
        teachStudentTeacher.setCourseModelId(obj.getId());
        baseService.save(teachStudentTeacher);
        return ResultGenerator.genSuccessResult();
    }


}
