package com.honorfly.schoolsys.controller;

import com.honorfly.schoolsys.entry.User;
import com.honorfly.schoolsys.form.SysPermissionForm;
import com.honorfly.schoolsys.service.IUserService;
import com.honorfly.schoolsys.utils.AppConst;
import com.honorfly.schoolsys.utils.JSONHelper;
import com.honorfly.schoolsys.utils.service.Page;
import com.honorfly.schoolsys.utils.web.BaseController;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private IUserService userServiceImpl;

    @ApiOperation(value="商店基本资料提交")
    @ResponseBody
    @RequestMapping(value = "/saveMallBaseInfo",method = RequestMethod.POST)
    public void saveMallBaseInfo(@RequestBody @Valid SysPermissionForm sysPermissionForm, BindingResult bindingResult){
        try {
            if(bindingResult.hasErrors()){
                JSONHelper.returnInfo(JSONHelper.returnServerErrJsonString(bindingResult.getFieldError().getDefaultMessage()));
                return;
            }
            JSONHelper.returnInfo(JSONHelper.returnServerSuccessJsonString());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            JSONHelper.returnInfo(JSONHelper.returnServerErrJsonString());
            return;
        }
    }
}
