package com.honorfly.schoolsys.service;

import com.honorfly.schoolsys.entry.SysRole;
import com.honorfly.schoolsys.entry.SysUser;
import com.honorfly.schoolsys.utils.service.IBaseService;
import com.honorfly.schoolsys.utils.service.Page;

import java.util.List;
import java.util.Map;




public interface ISysUserService extends IBaseService {
	public Page roleListByPage(Map<String,String> search, int currentPage, int pageSize) throws Exception;

	public Page userPageList(Map where, int currentPage, int pageSize) throws Exception;

	public List userList(String search) throws Exception;

	public List listRoles(String search) throws Exception;

	public List listRolesByIDString(String idString) throws Exception;


	public Page sysLogListByPage(String search, int currentPage, int pageSize) throws Exception;

	public SysUser queryUserByName(String name) throws Exception;

	public List listProxy(String search) throws Exception;

	public SysRole getRoleByName(String name) throws Exception ;

	public Page userPayListByPage(Map<String, String> where, int currentPage, int pageSize) throws Exception;

	public SysUser queryUserByParentId(String parentId) throws Exception ;


	public List listRolesByName(String name) throws Exception ;

	public List listProxy2(Map<String, String> where) throws Exception ;

	public List roleList(Map where) throws Exception;
}
