package com.honorfly.schoolsys.controller;

import com.honorfly.schoolsys.entry.SysPermission;
import com.honorfly.schoolsys.form.IDForm;
import com.honorfly.schoolsys.form.SysPermissionForm;
import com.honorfly.schoolsys.form.UserLoginForm;
import com.honorfly.schoolsys.utils.AppConst;
import com.honorfly.schoolsys.utils.Result;
import com.honorfly.schoolsys.utils.ResultGenerator;
import com.honorfly.schoolsys.utils.service.BaseService;
import com.honorfly.schoolsys.utils.web.BaseController;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/sysManager")
public class SysManagerController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BaseService baseService;

    @ApiOperation(value="用戶登錄")
    @ResponseBody
    @RequestMapping(value = "/userLogin",method = RequestMethod.POST)
    public Result userLogin(@RequestBody @Valid UserLoginForm userLoginForm, BindingResult bindingResult) throws Exception{
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        /*SysUser user = baseService.userLogin(userName, passWord);
        if(user==null){
            return ResultGenerator.genFailResult("账号或密码错误");
        }*//*SysUser user = baseService.userLogin(userName, passWord);
        if(user==null){
            return ResultGenerator.genFailResult("账号或密码错误");
        }*/

        /*BeanUtils.copyProperties(userLoginForm,user);
        HttpSession session = request.getSession();
        baseService.save(user);
        session.setAttribute(AppConst.USER_KEY, user);*/
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value="保存操作URL信息")
    @ResponseBody
    @RequestMapping(value = "/saveSysPermission",method = RequestMethod.POST)
    public Result saveSysPermission(@RequestBody @Valid SysPermissionForm sysPermissionForm, BindingResult bindingResult) throws Exception{
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        SysPermission sysPermission = new SysPermission();
        BeanUtils.copyProperties(sysPermissionForm,sysPermission);
        baseService.save(sysPermission);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value="通过id查询")
    @ResponseBody
    @RequestMapping(value = "/querySysPermission",method = RequestMethod.POST)
    public Result querySysPermission(@RequestBody @Valid IDForm IDForm, BindingResult bindingResult) throws Exception{
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        SysPermission sysPermissionForm = baseService.getById(SysPermission.class,IDForm.getId());
        if(null == sysPermissionForm){
            return ResultGenerator.genFailResult(AppConst.NOFIND_ERR_MSG);
        }
        return ResultGenerator.genSuccessResult(sysPermissionForm);
    }

    @ApiOperation(value="通过id删除")
    @ResponseBody
    @RequestMapping(value = "/deleteSysPermission",method = RequestMethod.POST)
    public Result deleteSysPermission(@RequestBody @Valid IDForm IDForm, BindingResult bindingResult) throws Exception{
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        SysPermission sysPermission = baseService.getById(SysPermission.class,IDForm.getId());
        if(null == sysPermission){
            return ResultGenerator.genFailResult(AppConst.NOFIND_ERR_MSG);
        }
        //baseService.delete(sysPermission);
        return ResultGenerator.genSuccessResult(sysPermission);
    }


}
