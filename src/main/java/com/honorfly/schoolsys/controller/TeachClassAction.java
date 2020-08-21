

package com.honorfly.schoolsys.controller;


import com.honorfly.schoolsys.entry.*;
import com.honorfly.schoolsys.service.ITeachService;
import com.honorfly.schoolsys.utils.DateUtil;
import com.honorfly.schoolsys.utils.Result;
import com.honorfly.schoolsys.utils.ResultGenerator;
import com.honorfly.schoolsys.utils.web.BaseController;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/teachClasses/")
public class TeachClassAction extends BaseController {

    @Autowired
    ITeachService teachService;
    @Bean
    public TeachClasses setTeachClasses() throws BeansException {
        entityObjClazz = TeachClasses.class;
        return  null;
    }

    @ApiOperation(value="新增")
    @ResponseBody
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public Result save(@RequestBody Map<String,Object> args) throws Exception{
        TeachClasses obj = (TeachClasses) entityObjClazz.newInstance();
        for(Map.Entry<String, Object> entry : args.entrySet()){
            obj.setPropertyValue(entry.getKey(),entry.getValue());
        }
        obj.setId(null);
        Long teacherId = Long.valueOf(args.get("teacherId").toString());
        if(teacherId!=null&&teacherId>0){
            SysUser teacher = baseService.getById(SysUser.class,teacherId);
            obj.setTeacher(teacher);
        }
        Long teachCourseId = Long.valueOf(args.get("teachCourseId").toString());
        if(teachCourseId!=null&&teachCourseId>0){
            TeachCourse teachCourse = baseService.getById(TeachCourse.class,teachCourseId);
            obj.setTeachCourse(teachCourse);
        }
        if(args.get("startDate")!=null){
            String startDate = args.get("startDate").toString();
            Date s = DateUtil.getDateByStr(startDate);
            obj.setStartDate(s);
        }
        if(args.get("endDate")!=null){
            String endDate = args.get("endDate").toString();
            Date e = DateUtil.getDateByStr(endDate);
            obj.setEndDate(e);
        }
        baseService.save(obj);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value="修改")
    @ResponseBody
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    public Result edit(@RequestBody Map<String,Object> args) throws Exception{
        TeachClasses obj = (TeachClasses) baseService.getById(entityObjClazz,Long.parseLong(args.get("id").toString()));
        for(Map.Entry<String, Object> entry : args.entrySet()){
            obj.setPropertyValue(entry.getKey(),entry.getValue());
        }
        Long teacherId = Long.valueOf(args.get("teacherId").toString());
        if(teacherId!=null&&teacherId>0){
            SysUser teacher = baseService.getById(SysUser.class,teacherId);
            obj.setTeacher(teacher);
        }
        Long teachCourseId = Long.valueOf(args.get("teachCourseId").toString());
        if(teachCourseId!=null&&teachCourseId>0){
            TeachCourse teachCourse = baseService.getById(TeachCourse.class,teachCourseId);
            obj.setTeachCourse(teachCourse);
        }
        if(args.get("startDate")!=null){
            String startDate = args.get("startDate").toString();
            Date s = DateUtil.getDateByStr(startDate);
            obj.setStartDate(s);
        }
        if(args.get("endDate")!=null){
            String endDate = args.get("endDate").toString();
            Date e = DateUtil.getDateByStr(endDate);
            obj.setEndDate(e);
        }

        baseService.edit(obj);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value="没有选择的学员列表")
    @ResponseBody
    @RequestMapping(value = "/noClassSelectStudentList",method = RequestMethod.POST)
    public Result noClassSelectStudentList(String search, Map<String,String> args) throws Exception{
        StringBuffer sql = new StringBuffer();
        sql.append("select student.*  from market_student student left join  teach_student_course studentcourse on student.id=studentcourse.student_id where ");
        sql.append(space);
        sql.append("student.admin_id="+getRedisSession().getAdminId());
        sql.append(space);
        sql.append("and student.invalid=true");
        sql.append(space);
        sql.append("and studentcourse.course_id="+Long.valueOf(search));
        sql.append(space);
        sql.append("and studentcourse.class_id=0");
        sql.append(space);
        sql.append("order by student.id desc");
        List resultList = baseService.loadBySQL(sql.toString(),args, MarketStudent.class);
        return ResultGenerator.genSuccessResult(resultList);
    }


    @ApiOperation(value="班级增加学生")
    @ResponseBody
    @RequestMapping(value = "/studentAddToClass",method = RequestMethod.POST)
    public Result studentAddToClass(long classId,long courseId,String studentIds) throws Exception{
        if(StringUtils.isBlank(studentIds)){
            return ResultGenerator.genSuccessResult();
        }
        String[] str = studentIds.split(",");
        for (String studentId : str){
            TeachStudentCourse teachStudentCourse = teachService.queryCount(Long.valueOf(studentId), courseId);
            teachStudentCourse.setClassId(classId);
            baseService.edit(teachStudentCourse);
        }
        TeachClasses teachClasses = baseService.getById(TeachClasses.class,classId);
        teachClasses.setPersonCountCurrent(baseService.getSQLTotalCnt("select count(*) from teach_student_course where class_id="+classId));
        baseService.edit(teachClasses);
        return ResultGenerator.genSuccessResult();
    }
}
