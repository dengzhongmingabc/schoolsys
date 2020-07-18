

package com.honorfly.schoolsys.controller;


import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.honorfly.schoolsys.entry.MarketStudent;
import com.honorfly.schoolsys.utils.Result;
import com.honorfly.schoolsys.utils.ResultGenerator;
import com.honorfly.schoolsys.utils.web.BaseController;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;


@Controller
@RequestMapping("/marketStudent/")
public class MarketStudentAction extends BaseController {
    @Bean
    public MarketStudent setMarketStudent() throws BeansException {
        entityObjClazz = MarketStudent.class;
        return  null;
    }

    @ApiOperation(value="新增")
    @ResponseBody
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public Result save(@RequestBody Map<String,Object> args) throws Exception{
        MarketStudent obj = new MarketStudent();
        for(Map.Entry<String, Object> entry : args.entrySet()){
            obj.setPropertyValue(entry.getKey(),entry.getValue());
        }

        System.out.println(args.get("seekCourse"));
        obj.setSeekCourse(JSONObject.parseArray(JSONUtils.toJSONString(args.get("seekCourse"))));
        List talks = new ArrayList();
        Map talk = new HashMap();
        talk.put("createdTime",new Date());
        talk.put("createId",getRedisSession().getId());
        talk.put("createName",getRedisSession().getRealName());
        talk.put("createUserName",getRedisSession().getUsername());
        talk.put("content",args.get("talkMark").toString());
        talks.add(talk);
        obj.setTalkMark(talks);

        obj.setId(null);
        baseService.save(obj);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value="修改")
    @ResponseBody
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    public Result edit(@RequestBody Map<String,Object> args) throws Exception{
        MarketStudent obj =  baseService.getById(MarketStudent.class,Long.parseLong(args.get("id").toString()));
        for(Map.Entry<String, Object> entry : args.entrySet()){
            obj.setPropertyValue(entry.getKey(),entry.getValue());
        }

        System.out.println(args.get("seekCourse"));
        obj.setSeekCourse(JSONObject.parseArray(JSONUtils.toJSONString(args.get("seekCourse"))));
        List talks = new ArrayList();
        Map talk = new HashMap();
        talk.put("createdTime",new Date());
        talk.put("createId",getRedisSession().getId());
        talk.put("createName",getRedisSession().getRealName());
        talk.put("createUserName",getRedisSession().getUsername());
        talk.put("content",args.get("talkMark").toString());
        talks.add(talk);
        obj.setTalkMark(talks);
        baseService.edit(obj);
        return ResultGenerator.genSuccessResult();
    }

}
