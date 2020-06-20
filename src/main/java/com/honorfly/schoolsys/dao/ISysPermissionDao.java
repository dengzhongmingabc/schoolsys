package com.honorfly.schoolsys.dao;

import com.honorfly.schoolsys.entry.SysRole;
import com.honorfly.schoolsys.entry.SysUser;
import com.honorfly.schoolsys.utils.dao.IBaseDao;

import java.util.List;




public interface ISysPermissionDao extends IBaseDao {

	public void addPermission(SysRole role, String[] newPermission);

	public void delPermission(SysRole role, String[] oldPermission);

	public SysUser userLogin(String userName, String password) throws Exception;
	public List queryParent() throws Exception;
	public List listByParentId(long parentId) throws Exception;


	public List listSysPermissionByIDString(String idString) throws Exception;


	public List rolePermissionByRoleID(String roleId) throws Exception;




	public void delPermission(long delPermissionId) throws Exception  ;

	public List allPermission() throws Exception  ;

	public void editRoleBatch(String idString,int invalid) throws Exception;

}
