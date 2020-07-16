

package com.honorfly.schoolsys.controller;


import com.honorfly.schoolsys.entry.TeachCourseType;
import com.honorfly.schoolsys.utils.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/teachCourseType/")
public class TeachCourseTypeAction extends BaseController {
    {
        entityObjClazz = TeachCourseType.class;
    }

/*
    @ApiOperation(value="分页查询")
    @ResponseBody
    @RequestMapping(value = "/pageList",method = RequestMethod.POST)
    public Result pageList() throws Exception{

        return ResultGenerator.genSuccessResult("imdsfdasfds");
    }
*/

}
