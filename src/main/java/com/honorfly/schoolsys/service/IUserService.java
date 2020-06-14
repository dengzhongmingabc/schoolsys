package com.honorfly.schoolsys.service;

import com.honorfly.schoolsys.utils.service.IBaseService;

import java.util.List;

public interface IUserService extends IBaseService {
	public List query(String userName, String password)throws Exception;
}
