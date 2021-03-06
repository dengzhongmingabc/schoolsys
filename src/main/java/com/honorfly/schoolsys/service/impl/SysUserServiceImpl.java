package com.honorfly.schoolsys.service.impl;

import com.honorfly.schoolsys.dao.ISysUserDao;
import com.honorfly.schoolsys.entry.SysRole;
import com.honorfly.schoolsys.entry.SysUser;
import com.honorfly.schoolsys.service.ISysUserService;
import com.honorfly.schoolsys.utils.service.BaseService;
import com.honorfly.schoolsys.utils.service.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("sysUserService")
public class SysUserServiceImpl extends BaseService implements ISysUserService {

	@Autowired
	private ISysUserDao sysUserDao;

	public Page roleListByPage(Map<String,String> search, int currentPage, int pageSize) throws Exception {
		return sysUserDao.roleListByPage(search, currentPage, pageSize);
    }

	public Page userPageList(Map where,int currentPage,int pageSize) throws Exception {
		return sysUserDao.userPageList(where, currentPage, pageSize);
    }

	public List roleList() throws Exception {
		StringBuffer sbsql = new StringBuffer(" select role.* from sys_role role where 1=1 ");
		sbsql.append(" and invalid = true ");
		sbsql.append(" and is_lock = false ");
		sbsql.append(" and admin_id = " + baseDaoImpl.getAdminId() + " ");
		sbsql.append(" order by id desc");
		return baseDaoImpl.loadBySQL(sbsql.toString(),SysRole.class);
	}

	public Page userPayListByPage(Map<String,String> where,int currentPage,int pageSize) throws Exception {
		return sysUserDao.userPayListByPage(where, currentPage, pageSize);
	}



	public List userList(String search) throws Exception {
		return sysUserDao.userList(search);
    }

	public List listRoles(String search) throws Exception {
		return sysUserDao.listRoles(search);
    }


	public SysUser queryUserByName(String name) throws Exception{
		return sysUserDao.queryUserByName(name);
	}

	public List listRolesByIDString(String idString) throws Exception {
		return sysUserDao.listRolesByIDString(idString);
    }

	public List listRolesByName(String name) throws Exception {
		return sysUserDao.listRolesByName(name);
	}
	@Override
	public Page sysLogListByPage(String search, int currentPage, int pageSize)
			throws Exception {
		return sysUserDao.sysLogListByPage(search, currentPage, pageSize);
	}


	public List listProxy(String search) throws Exception {
		return sysUserDao.listProxy(search);
	}

	public SysUser queryUserByParentId(String parentId) throws Exception {
		return sysUserDao.queryUserByParentId(parentId);
	}

	public SysRole getRoleByName(String name) throws Exception {
		return sysUserDao.getRoleByName(name);
	}

	@Override
	public List listProxy2(Map<String, String> where) throws Exception {
		return sysUserDao.listProxy2(where);
	}
}
