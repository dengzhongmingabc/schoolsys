package com.honorfly.schoolsys.service.impl;

import com.honorfly.schoolsys.dao.ISysPermissionDao;
import com.honorfly.schoolsys.entry.SysRole;
import com.honorfly.schoolsys.entry.SysUser;
import com.honorfly.schoolsys.service.ISysPermissionService;
import com.honorfly.schoolsys.utils.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.Map.Entry;


@Service("sysPermissionService")
public class SysPermissionServiceImpl extends BaseService implements ISysPermissionService {

	@Autowired
	private ISysPermissionDao spDao;

	public void updateRole(SysRole role, String[] addPermission, String[] delPermission) throws Exception{
		spDao.delPermission(role, delPermission);
		spDao.addPermission(role, addPermission);
	}

	public void update(SysRole role,String[] newPermission,String[] oldPermission) throws Exception{
		String[] result_insect = intersect(newPermission, oldPermission);

		String[] new_minus = minus(result_insect, newPermission);
		String[] old_minus = minus(result_insect, oldPermission);
		this.updateRole(role, new_minus, old_minus);
	}

	public String[] getNewMinus(String[] newPermission,String[] oldPermission){
		String[] result_insect = intersect(newPermission, oldPermission);
		String[] new_minus = minus(result_insect, newPermission);

		return new_minus;
	}

	public String[] getOldMinus(String[] newPermission,String[] oldPermission){
		String[] result_insect = intersect(newPermission, oldPermission);
		String[] old_minus = minus(result_insect, oldPermission);

		return old_minus;
	}

	public void update(SysRole role,String newPermission,String oldPermission){

	}


	    public static void main(String[] args) {
	        //测试union
	        String[] arr1 = {"abc", "df"};
	        String[] arr2 = {"abc", "cc", "df", "d"};
	        String[] result_union = union(arr1, arr2);
	        for (String str : result_union) {
	            System.out.println(str);
	        }
	        System.out.println("---------------------可爱的分割线------------------------");

	        //测试insect
	        String[] result_insect = intersect(arr1, arr2);
	        for (String str : result_insect) {
	            System.out.println(str);
	        }

	         System.out.println("---------------------疯狂的分割线------------------------");
	          //测试minus
	        String[] result_minus = minus(arr1, arr2);
	        for (String str : result_minus) {
	            System.out.println(str);
	        }
	    }

	    //求两个字符串数组的并集，利用set的元素唯�??
	    public static String[] union(String[] arr1, String[] arr2) {
	        Set<String> set = new HashSet<String>();
	        for (String str : arr1) {
	            set.add(str);
	        }
	        for (String str : arr2) {
	            set.add(str);
	        }
	        String[] result = {};
	        return set.toArray(result);
	    }

	    //求两个数组的交集
	    public static String[] intersect(String[] arr1, String[] arr2) {
	        Map<String, Boolean> map = new HashMap<String, Boolean>();
	        LinkedList<String> list = new LinkedList<String>();
	        for (String str : arr1) {
	            if (!map.containsKey(str)) {
	                map.put(str, Boolean.FALSE);
	            }
	        }
	        for (String str : arr2) {
	            if (map.containsKey(str)) {
	                map.put(str, Boolean.TRUE);
	            }
	        }

	        for (Entry<String, Boolean> e : map.entrySet()) {
	            if (e.getValue().equals(Boolean.TRUE)) {
	                list.add(e.getKey());
	            }
	        }

	        String[] result = {};
	        return list.toArray(result);
	    }

	    //求两个数组的差集
	    public static String[] minus(String[] arr1, String[] arr2) {
	        LinkedList<String> list = new LinkedList<String>();
	        LinkedList<String> history = new LinkedList<String>();
	        String[] longerArr = arr1;
	        String[] shorterArr = arr2;
	        //找出较长的数组来减较短的数组
	        if (arr1.length > arr2.length) {
	            longerArr = arr2;
	            shorterArr = arr1;
	        }
	        for (String str : longerArr) {
	            if (!list.contains(str)) {
	                list.add(str);
	            }
	        }
	        for (String str : shorterArr) {
	            if (list.contains(str)) {
	                history.add(str);
	                list.remove(str);
	            } else {
	                if (!history.contains(str)) {
	                    list.add(str);
	                }
	            }
	        }

	        String[] result = {};
	        return list.toArray(result);
	    }

		@Override
		public SysUser userLogin(String userName, String password)
				throws Exception {
			return spDao.userLogin(userName, password);
		}

	public SysUser loadUser(String userName) throws Exception {
		StringBuffer sql = new StringBuffer("select * from sys_user sysuser where 1=1 ");
		sql.append(" and sysuser.user_name=:userName ");
		Map args = new HashMap();
		args.put("userName", userName);
		List<SysUser> users =  this.loadBySQL(sql.toString(), args, SysUser.class);
		if(null!=users&&users.size()>0){
			return users.get(0);
		}
		return null;
	}

		@Override
		public List queryParent() throws Exception {
			return spDao.queryParent();
		}

		@Override
		public List listByParentId(Long parentId) throws Exception {
			return spDao.listByParentId(parentId);
		}

		@Override
		public List listSysPermissionByIDString(String idString)
				throws Exception {
			return spDao.listSysPermissionByIDString(idString);
		}

		@Override
		public List rolePermissionByRoleID(String roleId) throws Exception {
			return spDao.rolePermissionByRoleID(roleId);
		}




		@Transactional(propagation=Propagation.REQUIRED)
		public void delPermission(long delPermissionId)  throws Exception {
			 spDao.delPermission(delPermissionId);
		}


	public List allPermission() throws Exception{
		return spDao.allPermission();
	}

	public void editRoleBatch(String idString,int invalid) throws Exception {
		spDao.editRoleBatch(idString,invalid);
	}

}
