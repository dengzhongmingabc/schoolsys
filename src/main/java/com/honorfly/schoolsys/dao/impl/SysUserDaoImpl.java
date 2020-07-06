package com.honorfly.schoolsys.dao.impl;


import com.honorfly.schoolsys.dao.ISysUserDao;
import com.honorfly.schoolsys.entry.SysRole;
import com.honorfly.schoolsys.entry.SysUser;
import com.honorfly.schoolsys.utils.dao.BaseDaoImpl;
import com.honorfly.schoolsys.utils.service.Page;
import com.honorfly.schoolsys.utils.service.PageFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SysUserDaoImpl extends BaseDaoImpl implements ISysUserDao {




	public Page roleListByPage(Map<String,String> search, int currentPage, int pageSize) throws Exception {
		StringBuffer sbsql = new StringBuffer(" select role.* from sys_role role where 1=1 ");
		Map args = new HashMap();
		if(!StringUtils.isBlank(search.get("name"))){
			sbsql.append(" and  role.role_name like :content");
			args.put("content", "%"+search.get("name")+"%");
		}
		if(!StringUtils.isBlank(search.get("invalid"))){
			sbsql.append(" and  role.invalid = :invalid");
			args.put("invalid", Boolean.valueOf(search.get("invalid")));
		}
		sbsql.append(" and admin_id = " + getAdminId() + " ");
		sbsql.append(" order by role.created_date desc");
		return PageFactory.createPageBySql(this, sbsql.toString(), args, SysRole.class, currentPage, pageSize);
    }

	public SysRole getRoleByName(String name) throws Exception {
		StringBuffer sbsql = new StringBuffer(" select sysuser.* from sys_role sysuser where 1=1 ");
		Map args = new HashMap();
		if(!StringUtils.isBlank(name)){
			sbsql.append(" and  sysuser.role_name like :content");
			args.put("content", name);
		}
		List<SysRole> l = this.loadBySQL(sbsql.toString(), args, SysRole.class);
		if(l!=null&&l.size()>0){
			return l.get(0);
		}
		return null;
    }

	public Page userPageList(Map<String,String> where,int currentPage,int pageSize) throws Exception {
		StringBuffer sbsql = new StringBuffer(" select sysuser.* from sys_user sysuser where 1=1 ");
		Map args = new HashMap();
		if(!StringUtils.isBlank(where.get("userName"))){
			sbsql.append(" and  (sysuser.user_name like :userName) ");
			args.put("userName", "%"+where.get("userName")+"%");
		}
		if(!StringUtils.isBlank(where.get("mobile"))){
			sbsql.append(" and  (sysuser.mobile like :mobile) ");
			args.put("mobile", "%"+where.get("mobile")+"%");
		}
		if(!StringUtils.isBlank(where.get("realName"))){
			sbsql.append(" and  (sysuser.real_name like :realName) ");
			args.put("realName", "%"+where.get("realName")+"%");
		}
		//sbsql.append(" and invalid = true ");
		//sbsql.append(" and school_id = "+getSchoolId()+" ");
		sbsql.append(" and admin_id = " + getAdminId() + " ");
		sbsql.append(" order by id desc");

		return PageFactory.createPageBySql(this, sbsql.toString(), args, SysUser.class, currentPage, pageSize);
    }



	public Page userPayListByPage(Map<String,String> where,int currentPage,int pageSize) throws Exception {
		StringBuffer sbsql = new StringBuffer(" select s.*,c.mobile,c.name from t_proxy_transaction_statements s left join ( (select id ,1 as type,mobile,real_name as name from t_consignor_info )  union (select id ,2 as type,mobile ,driver_name as name from t_truck_info ) union (select id ,3 as type,user_name as mobile ,real_name as name from sys_user )) c on s.user_id=c.id and c.type=s.user_type where 1=1 ");
		Map args = new HashMap();

		if(!StringUtils.isBlank(where.get("sysUserId"))){
			sbsql.append(" and  s.account_id = :sysUserId");
			args.put("sysUserId", Long.valueOf(where.get("sysUserId")));
		}
		if(!StringUtils.isBlank(where.get("payType"))&&where.get("payType").equals("balance")){
			sbsql.append(" and  (s.pay <> 0 or s.pay is not null) ");
			if(!StringUtils.isBlank(where.get("isOut"))&&where.get("isOut").equals("yes")){
				sbsql.append(" and  (s.pay < 0) ");
			}else if(!StringUtils.isBlank(where.get("isOut"))&&where.get("isOut").equals("no")){
				sbsql.append(" and  (s.pay > 0) ");
			}
		}
		if(!StringUtils.isBlank(where.get("payType"))&&where.get("payType").equals("point")){
			sbsql.append(" and  (s.points <> 0 or s.points is not null) ");
			if(!StringUtils.isBlank(where.get("isOut"))&&where.get("isOut").equals("yes")){
				sbsql.append(" and  (s.points < 0) ");
			}else if(!StringUtils.isBlank(where.get("isOut"))&&where.get("isOut").equals("no")){
				sbsql.append(" and  (s.points > 0) ");
			}
		}
		if(!StringUtils.isBlank(where.get("search"))){
			sbsql.append(" and  c.name like :content");
			args.put("content", "%"+where.get("search")+"%");
		}
		if(!StringUtils.isBlank(where.get("mobile"))){
			sbsql.append(" and  c.mobile like :content");
			args.put("content", "%"+where.get("mobile")+"%");
		}
		sbsql.append(" order by s.id desc");
		return PageFactory.createMapPageBySql(this, sbsql.toString(), args, currentPage, pageSize);
		//return PageFactory.createPageBySql(this, sbsql.toString(), args,TProxyTransactionStatements.class, currentPage, pageSize);
    }




	public List userList(String search) throws Exception {
		StringBuffer sbsql = new StringBuffer(" select sysuser.* from sys_user sysuser where 1=1 ");
		Map args = new HashMap();
		if(!StringUtils.isBlank(search)){
			sbsql.append(" and  sysuser.real_name like :content");
			args.put("content", "%"+search+"%");
		}
		sbsql.append(" order by sysuser.created_date desc");
		return this.loadBySQL(sbsql.toString(), args, SysUser.class);
    }

	public SysUser queryUserByName(String name) throws Exception {
		StringBuffer sbsql = new StringBuffer(" select sysuser.* from sys_user sysuser where sysuser.user_name='"+name+"'");
		Map args = new HashMap();
		List<SysUser> l = this.loadBySQL(sbsql.toString(), args, SysUser.class);
		if(l!=null&&l.size()>0){
			return l.get(0);
		}
		return null;
    }


	public SysUser queryUserByParentId(String parentId) throws Exception {
		StringBuffer sbsql = new StringBuffer(" select sysuser.* from sys_user sysuser where sysuser.parent_id="+parentId);
		Map args = new HashMap();
		List<SysUser> l = this.loadBySQL(sbsql.toString(), args, SysUser.class);
		if(l!=null&&l.size()>0){
			return l.get(0);
		}
		return null;
    }

	public List listRoles(String search) throws Exception {
		StringBuffer sbsql = new StringBuffer(" select role.* from sys_role role  where 1=1 ");
		Map args = new HashMap();
		if(!StringUtils.isBlank(search)){
			sbsql.append(" and  role.name like :content");
			args.put("content", "%"+search+"%");
		}
		sbsql.append(" order by role.created_date desc");
		return this.loadBySQL(sbsql.toString(), args, SysRole.class);
    }


	public List listProxy(String search) throws Exception {
		StringBuffer sbsql = new StringBuffer(" select sysuser.* from sys_user sysuser where 1=1 and department='A0001' and proxy_admin = true ");
		Map args = new HashMap();
		if(!StringUtils.isBlank(search)){
			sbsql.append(" and  sysuser.user_name like :content");
			args.put("content", "%"+search+"%");
		}
		sbsql.append(" order by sysuser.created_date desc");
		return this.loadBySQL(sbsql.toString(), args, SysUser.class);
    }

	public List listRolesByIDString(String idString) throws Exception {
		StringBuffer sb = new StringBuffer("select dd.* from sys_role dd where id in(");
		sb.append(idString);
		sb.append(")");
		return this.loadBySQL(sb.toString(), null, SysRole.class);
    }

	public List listRolesByName(String name) throws Exception {
		StringBuffer sb = new StringBuffer("select dd.* from sys_role dd where role_name='");
		sb.append(name);
		sb.append("'");
		return this.loadBySQL(sb.toString(), null, SysRole.class);
    }


	/*public Page sysLogListByPage(String search,int currentPage,int pageSize) throws Exception {
		StringBuffer sql = new StringBuffer(" select suser.real_name,suser.user_name,sysuser.* from sys_user suser join sys_user_operation sysuser on suser.id=sysuser.user_id where 1=1 ");
		Map args = new HashMap();
		if(!StringUtils.isBlank(search)){
			sql.append(" and  user.real_name like :content");
			args.put("content", "%"+search+"%");
		}
		sql.append(" order by sysuser.created_date desc");
		return PageFactory.createMapPageBySql(this, sql.toString(), args, currentPage, pageSize);
    }*/

	public Page sysLogListByPage(String search,int currentPage,int pageSize) throws Exception {
		StringBuffer sql = new StringBuffer(" select id, user_ip as userIp,dated,logger,log_level as logLevel,(message->>'realName') as realName,(message->>'userName') as userName,(message->>'message') as message,(message->>'userId') as userId from sys_logs  where 1=1  ");
		Map args = new HashMap();
		if(!StringUtils.isBlank(search)){
			sql.append(" and  (message->>'realName') LIKE '%"+search+"%'");
		}
		sql.append(" order by dated desc");
		return PageFactory.createMapPageBySql(this, sql.toString(), args, currentPage, pageSize);
    }

	@Override
	public List listProxy2(Map<String, String> where) throws Exception {
		StringBuffer sbsql = new StringBuffer(" select sysuser.* from sys_user sysuser where 1=1 and department='A0001' and proxy_admin = true ");
		Map args = new HashMap();
		if(!StringUtils.isBlank(where.get("proxyName"))){
			sbsql.append(" and  sysuser.user_name like :proxyName");
			args.put("proxyName", "%"+where.get("proxyName")+"%");
		}
		sbsql.append(" order by sysuser.created_date desc");
		return this.loadBySQL(sbsql.toString(), args, SysUser.class);
    }

}
