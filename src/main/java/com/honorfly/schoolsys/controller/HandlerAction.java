

package com.honorfly.schoolsys.controller;


import com.honorfly.schoolsys.entry.HandlerOrder;
import com.honorfly.schoolsys.utils.web.BaseController;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/handler/")
public class HandlerAction extends BaseController {
    @Bean
    public HandlerOrder setHandlerOrder() throws BeansException {
        entityObjClazz = HandlerOrder.class;
        return  null;
    }

}
