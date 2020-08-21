

package com.honorfly.schoolsys.controller;


import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.honorfly.schoolsys.entry.HandlerOrder;
import com.honorfly.schoolsys.entry.MarketStudent;
import com.honorfly.schoolsys.entry.TeachStudentCourse;
import com.honorfly.schoolsys.service.ITeachService;
import com.honorfly.schoolsys.utils.Result;
import com.honorfly.schoolsys.utils.ResultGenerator;
import com.honorfly.schoolsys.utils.web.BaseController;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;


@Controller
@RequestMapping("/handler/")
public class HandlerAction extends BaseController {
    @Autowired
    ITeachService teachService;

    @Bean
    public HandlerOrder setHandlerOrder() throws BeansException {
        entityObjClazz = HandlerOrder.class;
        return null;
    }

    @ApiOperation(value = "新增")
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestBody Map<String, Object> args) throws Exception {
        HandlerOrder obj = (HandlerOrder) entityObjClazz.newInstance();
        for (Map.Entry<String, Object> entry : args.entrySet()) {
            obj.setPropertyValue(entry.getKey(), entry.getValue());
        }
        obj.setOrderNo(RandomStringUtils.randomNumeric(20));
        obj.setOrderContent(JSONObject.parseArray(JSONUtils.toJSONString(args.get("orderContent"))));
        obj.setId(null);
        //
        Long studentId = Long.valueOf(args.get("studentId").toString());
        MarketStudent marketStudent = baseService.getById(MarketStudent.class, studentId);
        JSONArray jsonArray = JSONObject.parseArray(JSONUtils.toJSONString(args.get("orderContent")));
        for (Object json : jsonArray) {
            JSONArray array = (JSONArray) json;
            Object object = array.get(0);
            JSONObject record = (JSONObject) object;
            if (record != null) {
                Long courseId = record.getLongValue("key");
                int courseCount = record.getIntValue("courseCount") * record.getIntValue("number");
                Long classId = record.getLongValue("currentClass");
                TeachStudentCourse teachStudentCourse = teachService.queryCount(studentId, courseId);
                if (teachStudentCourse != null) {
                    teachStudentCourse.setCourseCount(teachStudentCourse.getCourseCount() + courseCount);
                } else {
                    teachStudentCourse = new TeachStudentCourse();
                    teachStudentCourse.setCourseCount(courseCount);
                }
                teachStudentCourse.setCourseId(courseId);
                teachStudentCourse.setClassId(classId);
                teachStudentCourse.setStudentId(studentId);
                baseService.save(teachStudentCourse);
            }
        }
        baseService.save(marketStudent);
        obj.setMarketStudent(marketStudent);
        baseService.save(obj);
        HandlerOrder order = baseService.getById(HandlerOrder.class, obj.getId());
        return ResultGenerator.genSuccessResult(order);
    }


/*
    @ApiOperation(value="新增")
    @ResponseBody
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public Result save(@RequestBody Map<String,Object> args) throws Exception{
        HandlerOrder obj = (HandlerOrder) entityObjClazz.newInstance();
        for(Map.Entry<String, Object> entry : args.entrySet()){
            obj.setPropertyValue(entry.getKey(),entry.getValue());
        }
        obj.setOrderNo(RandomStringUtils.randomNumeric(20));
        obj.setOrderContent(JSONObject.parseArray(JSONUtils.toJSONString(args.get("orderContent"))));
        obj.setId(null);

        //
        Long studentId = Long.valueOf(args.get("studentId").toString());
        MarketStudent marketStudent = baseService.getById(MarketStudent.class,studentId);
        //marketStudent.setInward(true);
        //baseService.save(marketStudent);
        Map buyCourseMap = new HashMap();
        if(marketStudent.getBuyCourse()!=null){
            buyCourseMap = JSONObject.parseObject(JSONUtils.toJSONString(marketStudent.getBuyCourse()),Map.class);
        }
        JSONArray jsonArray = JSONObject.parseArray(JSONUtils.toJSONString(args.get("orderContent")));
        for (Object json:jsonArray){
            JSONArray array = (JSONArray)json;
            Object object = array.get(0);
            JSONObject record = (JSONObject)object;
            if(record!=null){
                Long courseId = record.getLongValue("key");
                int courseCount = record.getIntValue("courseCount")*record.getIntValue("number");
                Long classId = record.getLongValue("currentClass");
                Map courseMap = (Map) buyCourseMap.get(courseId.toString());
                if(courseMap==null){
                    courseMap = new HashMap();
                    courseMap.put("courseId",courseId);
                    courseMap.put("courseId",courseId);
                    courseMap.put("courseCount",courseCount);
                    courseMap.put("classId",classId);
                }else{
                    courseMap.put("courseId",courseId);
                    courseMap.put("courseCount",Integer.valueOf(courseMap.get("courseCount").toString())+courseCount);
                    courseMap.put("classId",classId);
                }
                buyCourseMap.put(courseId.toString(),courseMap);
            }
        }
        marketStudent.setBuyCourse(JSONObject.parseObject(JSONUtils.toJSONString(buyCourseMap)));
        baseService.save(marketStudent);
        obj.setMarketStudent(marketStudent);
        baseService.save(obj);
        HandlerOrder order = baseService.getById(HandlerOrder.class,obj.getId());
        return ResultGenerator.genSuccessResult(order);
    }
*/


}
