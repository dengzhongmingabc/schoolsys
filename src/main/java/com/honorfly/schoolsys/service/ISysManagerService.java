package com.honorfly.schoolsys.service;

import com.honorfly.schoolsys.entry.User;
import com.honorfly.schoolsys.utils.service.IBaseService;
import com.honorfly.schoolsys.utils.service.Page;

import java.util.Map;

public interface ISysManagerService extends IBaseService {



    Page querySysPermissionPageList(Map<String, String> where, int currentPage, int pageSize)  throws Exception;
}