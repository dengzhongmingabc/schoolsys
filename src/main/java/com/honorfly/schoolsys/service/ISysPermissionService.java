package com.honorfly.schoolsys.service;

import com.honorfly.schoolsys.entry.SysRole;
import com.honorfly.schoolsys.entry.SysUser;
import com.honorfly.schoolsys.utils.service.IBaseService;

import java.util.List;


public interface ISysPermissionService extends IBaseService {
	public void update(SysRole role, String[] newPermission, String[] oldPermission) throws Exception;

	public String[] getNewMinus(String[] newPermission, String[] oldPermission);

	public String[] getOldMinus(String[] newPermission, String[] oldPermission);


	public SysUser userLogin(String userName, String password) throws Exception;
	public List queryParent() throws Exception;
	public List listByParentId(Long parentId) throws Exception;


	public List listSysPermissionByIDString(String idString) throws Exception;


	public List rolePermissionByRoleID(String roleId) throws Exception;

	public void delPermission(long delPermissionId)  throws Exception ;

	public List allPermission() throws Exception;

}
