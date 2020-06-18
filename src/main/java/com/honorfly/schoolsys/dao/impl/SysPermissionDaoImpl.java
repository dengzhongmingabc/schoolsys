package com.honorfly.schoolsys.dao.impl;

import com.honorfly.schoolsys.dao.ISysPermissionDao;
import com.honorfly.schoolsys.entry.SysPermission;
import com.honorfly.schoolsys.entry.SysRole;
import com.honorfly.schoolsys.entry.SysUser;
import com.honorfly.schoolsys.utils.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Repository
public class SysPermissionDaoImpl extends BaseDaoImpl implements ISysPermissionDao {

	@Override
	public void addPermission(SysRole role, String[] addPermission) {
		if(addPermission!=null&&addPermission.length>0){
			for(String id:addPermission){
				String addsql = "insert into sys_role_permission (role_id,permission_id,created_date) values('"+role.getId()+"','"+id+"',now())";
				entityManager.createNativeQuery(addsql).executeUpdate();
			}
		}
	}

	@Override
	public void delPermission(SysRole role, String[] delPermission) {
		if(role.getId()!=null&&delPermission!=null&&delPermission.length>0){
			for(String id:delPermission){
				String delsql = "delete from sys_role_permission where role_id='"+role.getId()+"' and permission_id='"+id+"'";
				entityManager.createNativeQuery(delsql).executeUpdate();
			}
		}
	}

	@Override
	public void delPermission(long delPermissionId)  throws Exception {
		String delsql = "delete from sys_role_permission where permission_id='"+delPermissionId+"'";
		entityManager.createNativeQuery(delsql).executeUpdate();
	}

	public SysUser userLogin(String userName, String password) throws Exception {
		StringBuffer sql = new StringBuffer("select * from sys_user sysuser where 1=1 ");
		sql.append(" and sysuser.user_name=:userName ");
		Map args = new HashMap();
		args.put("userName", userName);
		//return this.getSQLTotalCnt(sql.toString(), args)>0;
		List<SysUser> users =  this.loadBySQL(sql.toString(), args, SysUser.class);
		if(null!=users&&users.size()>0){
			return users.get(0);
		}
		return null;
    }





	public List queryParent() throws Exception{
		String sql = " select dd.* from sys_permission dd where dd.parent_id=0 order by id asc";
		return this.loadBySQL(sql, null, SysPermission.class);
	}

	public List listByParentId(long parentId) throws Exception {
		String sql = " select dd.* from sys_permission dd where dd.parent_id=:pid ";
		Map args = new HashMap();
		args.put("pid", parentId);
		return this.loadBySQL(sql, args, SysPermission.class);
    }




	public List listSysPermissionByIDString(String idString) throws Exception {
		StringBuffer sb = new StringBuffer("select dd.* from sys_permission dd where id in(");
		/*String[] nps = idString.split(",");
		for(int i=0;i<nps.length;i+=1){
			if(i==nps.length-1){
				sb.append(nps[i].split("-")[1]);
			}else{
				sb.append(nps[i].split("-")[1]+",");
			}
		}*/
		sb.append(idString);
		sb.append(")");
		return this.loadBySQL(sb.toString(), null, SysPermission.class);
    }

	public List rolePermissionByRoleID(String roleId) throws Exception {
		StringBuffer sb = new StringBuffer("select * from sys_role_permission where role_id='"+roleId+"'");
		return this.loadMapBySQL(sb.toString());
    }

	public List allPermission() throws Exception {
		StringBuffer sb = new StringBuffer("select * from sys_role_permission");
		return this.loadMapBySQL(sb.toString());
	}
}
