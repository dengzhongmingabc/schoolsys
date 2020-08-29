

package com.honorfly.schoolsys.controller;


import com.honorfly.schoolsys.entry.TeachStudentTeacher;
import com.honorfly.schoolsys.service.ITeachService;
import com.honorfly.schoolsys.utils.web.BaseController;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/teachOneByOne/")
public class TeachOneByOneAction extends BaseController {

    @Autowired
    ITeachService teachService;
    @Bean
    public TeachStudentTeacher setTeachStudentTeacher() throws BeansException {
        entityObjClazz = TeachStudentTeacher.class;
        return  null;
    }

    /*@ApiOperation(value="分页查询")
    @ResponseBody
    @RequestMapping(value = "/pageList",method = RequestMethod.POST)
    public Result pageList(String search,  Map<String,String> args, int pageNo, int pageSize) throws Exception{

        StringBuffer sql = new StringBuffer();
        sql.append("select * from teach_student_course course left join market_student student on course.student_id=student.id left join sys_user teacher on course.class_id_or_teacher_id=teacher.id where course.teach_type=1 ");

        sql.append(space);
        sql.append("student.admin_id="+getRedisSession().getAdminId());
        sql.append(space);
        sql.append(" and student.invalid=true");
        if (!StringUtils.isBlank(search)&&!search.contains(" or ")){
            sql.append(search);
        }
        sql.append(space);
        sql.append("order by course.id desc");


        Page page = baseService.getMapDataPageBySQL(sql.toString(),args,pageNo,pageSize);


        return ResultGenerator.genSuccessResult(page);
    }*/
}
