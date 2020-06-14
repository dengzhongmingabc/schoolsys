package com.honorfly.schoolsys.service.impl;

import com.honorfly.schoolsys.entry.SysUser;
import com.honorfly.schoolsys.service.IUserService;
import com.honorfly.schoolsys.utils.dao.IBaseDao;
import com.honorfly.schoolsys.utils.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserServiceImpl  extends BaseService implements IUserService {

	@Autowired
	private IBaseDao baseDaoImpl;

	public List query(String userName,String password) throws Exception {
		List list = baseDaoImpl.loadBySQL(" select * from sys_user where user_name='"+userName+"' and password='"+password+"'", null, SysUser.class);
		return list;
	}

}
