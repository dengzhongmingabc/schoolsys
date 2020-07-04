

package com.honorfly.schoolsys.controller;


import com.honorfly.schoolsys.entry.School;
import com.honorfly.schoolsys.entry.SessionUser;
import com.honorfly.schoolsys.service.ISchoolManagerService;
import com.honorfly.schoolsys.utils.AppConst;
import com.honorfly.schoolsys.utils.Result;
import com.honorfly.schoolsys.utils.ResultGenerator;
import com.honorfly.schoolsys.utils.web.BaseController;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/school/")
public class SchoolAction extends BaseController {

    @Autowired
    ISchoolManagerService schoolManagerService;

/*    @ApiOperation(value = "学校列表")
    @ResponseBody
    @RequestMapping(value = "/schoolList", method = RequestMethod.POST)
    public Result schoolList() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SessionUser sessionUser = (SessionUser) auth.getPrincipal();
        List<School> list = schoolManagerService.schoolList(sessionUser.getAdminId());

        List retList = new ArrayList();
        //分成三个一组
        List sonList = null;
        for (int i = 0; i < list.size(); i++) {
            if (i % 3 == 0) {
                sonList = new ArrayList();
                sonList.add(list.get(i));
                if(i==list.size()-1){
                    retList.add(sonList);
                }
            }
            if (i % 3 == 1) {
                sonList.add(list.get(i));
                if(i==list.size()-1){
                    retList.add(sonList);
                }
            }
            if (i % 3 == 2) {
                sonList.add(list.get(i));
                retList.add(sonList);
            }
        }

        return ResultGenerator.genSuccessResult(retList);
    }*/


    @ApiOperation(value = "校区列表")
    @ResponseBody
    @RequestMapping(value = "/schoolList", method = RequestMethod.POST)
    public Result schoolList() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SessionUser sessionUser = (SessionUser) auth.getPrincipal();
        SessionUser redisSession = (SessionUser) redisUtil.get(AppConst.Redis_Session_Namespace + sessionUser.getId());
        List<School> list = redisSession.schools;
        if(redisSession.getAdmin()){
            list = schoolManagerService.schoolList(sessionUser.getAdminId());
        }
        List retList = new ArrayList();
        //分成三个一组
        List sonList = null;
        for (int i = 0; i < list.size(); i++) {
            if (i % 3 == 0) {
                sonList = new ArrayList();
                sonList.add(list.get(i));
                if (i == list.size() - 1) {
                    retList.add(sonList);
                }
            }
            if (i % 3 == 1) {
                sonList.add(list.get(i));
                if (i == list.size() - 1) {
                    retList.add(sonList);
                }
            }
            if (i % 3 == 2) {
                sonList.add(list.get(i));
                retList.add(sonList);
            }
        }
        return ResultGenerator.genSuccessResult(retList);
    }

    @ApiOperation(value = "增加学校(只有学校admin校长才能操作)")
    @ResponseBody
    @RequestMapping(value = "/schoolAdd", method = RequestMethod.POST)
    public Result schoolAdd(School school) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SessionUser sessionUser = (SessionUser) auth.getPrincipal();
        school.setAdminId(sessionUser.getAdminId());
        school.setId(null);
        schoolManagerService.save(school);
        return ResultGenerator.genSuccessResult();
    }


    @ApiOperation(value = "逻辑删除学校(只有学校admin校长才能操作)")
    @ResponseBody
    @RequestMapping(value = "/schoolDelete", method = RequestMethod.POST)
    public Result schoolDelete(long schoolId) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SessionUser sessionUser = (SessionUser) auth.getPrincipal();
        schoolManagerService.delete(sessionUser.getId(), schoolId);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "设置学校ID在Session里")
    @ResponseBody
    @RequestMapping(value = "/schoolIDSetting", method = RequestMethod.POST)
    public Result schoolIDSetting(long schoolId) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SessionUser sessionUser = (SessionUser) auth.getPrincipal();
        SessionUser redisSession = (SessionUser) redisUtil.get(AppConst.Redis_Session_Namespace + sessionUser.getId());

        redisSession.setSchoolId(schoolId);
        redisUtil.set(AppConst.Redis_Session_Namespace + sessionUser.getId(), redisSession, appConfig.getSessionExpire());

        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "查询所有学校")
    @ResponseBody
    @RequestMapping(value = "/schoolQuery", method = RequestMethod.POST)
    public Result schoolQuery() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SessionUser sessionUser = (SessionUser) auth.getPrincipal();
        SessionUser redisSession = (SessionUser) redisUtil.get(AppConst.Redis_Session_Namespace + sessionUser.getId());
        List<School> list = schoolManagerService.schoolList(redisSession.getAdminId());
        return ResultGenerator.genSuccessResult(list);
    }

}
