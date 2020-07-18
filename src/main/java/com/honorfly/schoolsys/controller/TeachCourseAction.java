

package com.honorfly.schoolsys.controller;


import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.honorfly.schoolsys.entry.School;
import com.honorfly.schoolsys.entry.TeachCourse;
import com.honorfly.schoolsys.entry.TeachCourseType;
import com.honorfly.schoolsys.service.ISchoolManagerService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/teachCourse/")
public class TeachCourseAction extends BaseController {

    @Bean
    public TeachCourse setTeachCourse() throws BeansException {
        entityObjClazz = TeachCourse.class;
        return  null;
    }
    @Autowired
    ISchoolManagerService schoolManagerService;

    @ApiOperation(value="新增")
    @ResponseBody
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public Result save(@RequestBody Map<String,Object> args) throws Exception{
        TeachCourse obj = new TeachCourse();
        for(Map.Entry<String, Object> entry : args.entrySet()){
            obj.setPropertyValue(entry.getKey(),entry.getValue());
        }

        TeachCourseType type = baseService.getById(TeachCourseType.class,Long.valueOf(args.get("typeId").toString()));
        obj.setType(type);

        obj.setPayModel(JSONObject.parseObject(JSONUtils.toJSONString(args.get("payModel"))));
        if (Integer.valueOf(args.get("selectType").toString())==2){//选择是部分
            List<School> schools = new ArrayList<School>();
            if(!StringUtils.isBlank(args.get("schoolIdStr").toString())){
                schools = schoolManagerService.schoolList(this.getRedisSession().getAdminId(),args.get("schoolIdStr").toString());
            }
            obj.getSchools().clear();
            obj.getSchools().addAll(schools);
        }else {//选择的是全部
            List<School> schools = schoolManagerService.schoolList(getRedisSession().getAdminId());
            obj.getSchools().clear();
            obj.getSchools().addAll(schools);
        }
        obj.setId(null);
        baseService.save(obj);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value="修改")
    @ResponseBody
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    public Result edit(@RequestBody Map<String,Object> args) throws Exception{
        TeachCourse obj = baseService.getById(TeachCourse.class,Long.valueOf(args.get("id").toString()));
        for(Map.Entry<String, Object> entry : args.entrySet()){
            obj.setPropertyValue(entry.getKey(),entry.getValue());
        }
        TeachCourseType type = baseService.getById(TeachCourseType.class,Long.valueOf(args.get("typeId").toString()));
        obj.setType(type);
        obj.setPayModel(JSONObject.parseObject(JSONUtils.toJSONString(args.get("payModel"))));
        if (Integer.valueOf(args.get("selectType").toString())==2){//选择是部分
            List<School> schools = new ArrayList<School>();
            if(!StringUtils.isBlank(args.get("schoolIdStr").toString())){
                schools = schoolManagerService.schoolList(this.getRedisSession().getAdminId(),args.get("schoolIdStr").toString());
            }
            obj.getSchools().clear();
            obj.getSchools().addAll(schools);
        }else {//选择的是全部
            List<School> schools = schoolManagerService.schoolList(getRedisSession().getAdminId());
            obj.getSchools().clear();
            obj.getSchools().addAll(schools);
        }
        baseService.edit(obj);
        return ResultGenerator.genSuccessResult();
    }
}
