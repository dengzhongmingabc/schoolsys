package com.honorfly.schoolsys.controller;

import com.honorfly.schoolsys.entry.SysPermission;
import com.honorfly.schoolsys.entry.User;
import com.honorfly.schoolsys.form.SysPermissionForm;
import com.honorfly.schoolsys.service.IUserService;
import com.honorfly.schoolsys.utils.AppConst;
import com.honorfly.schoolsys.utils.JSONHelper;
import com.honorfly.schoolsys.utils.dao.EntityObj;
import com.honorfly.schoolsys.utils.service.BaseService;
import com.honorfly.schoolsys.utils.service.Page;
import com.honorfly.schoolsys.utils.web.BaseController;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/sysManager")
public class SysManagerController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private BaseService baseService;
    @ApiOperation(value="保存操作URL信息")
    @ResponseBody
    @RequestMapping(value = "/saveSysPermission",method = RequestMethod.POST)
    public void saveMallBaseInfo(@RequestBody @Valid SysPermissionForm sysPermissionForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            JSONHelper.returnInfo(JSONHelper.returnServerErrJsonString(bindingResult.getFieldError().getDefaultMessage()));
            return;
        }
        SysPermission sysPermission = new SysPermission();
        BeanUtils.copyProperties(sysPermissionForm,sysPermission);
        //baseService.save(sysPermission);
        JSONHelper.returnInfo(JSONHelper.returnServerSuccessJsonString());
    }
}
