package com.honorfly.schoolsys.service;

import com.honorfly.schoolsys.utils.service.IBaseService;

import java.util.List;

public interface ISchoolManagerService extends IBaseService {

    //查询列表
    List schoolList(long adminId) throws Exception;

    //逻辑删除
    void delete(Long adminId,long schoolId) throws Exception;

    List schoolList(long adminId,String schoolIdStr) throws Exception;
}
