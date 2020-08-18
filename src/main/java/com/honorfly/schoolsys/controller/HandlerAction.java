

package com.honorfly.schoolsys.controller;


import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.honorfly.schoolsys.entry.HandlerOrder;
import com.honorfly.schoolsys.entry.MarketStudent;
import com.honorfly.schoolsys.utils.Result;
import com.honorfly.schoolsys.utils.ResultGenerator;
import com.honorfly.schoolsys.utils.web.BaseController;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeansException;
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
    @Bean
    public HandlerOrder setHandlerOrder() throws BeansException {
        entityObjClazz = HandlerOrder.class;
        return  null;
    }



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

        Long studentId = Long.valueOf(args.get("studentId").toString());
        MarketStudent marketStudent = baseService.getById(MarketStudent.class,studentId);
        marketStudent.setInward(true);
        baseService.save(marketStudent);
        obj.setMarketStudent(marketStudent);
        baseService.save(obj);
        HandlerOrder order = baseService.getById(HandlerOrder.class,obj.getId());
        return ResultGenerator.genSuccessResult(order);
    }

}
