package com.honorfly.schoolsys.service;

import com.honorfly.schoolsys.entry.User;
import com.honorfly.schoolsys.utils.service.IBaseService;
import com.honorfly.schoolsys.utils.service.Page;

import java.util.Map;

public interface IUserService extends IBaseService {


    User queryByUserName(String userName)throws Exception;

    public Page queryUserPageList(Map<String,String> where, int currentPage, int pageSize)  throws Exception;
}