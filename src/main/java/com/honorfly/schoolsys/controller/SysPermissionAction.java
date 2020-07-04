package com.honorfly.schoolsys.controller;


import com.alibaba.fastjson.JSONArray;
import com.honorfly.schoolsys.entry.*;
import com.honorfly.schoolsys.form.IDForm;
import com.honorfly.schoolsys.form.SysPermissionForm;
import com.honorfly.schoolsys.form.SysRoleForm;
import com.honorfly.schoolsys.service.ISchoolManagerService;
import com.honorfly.schoolsys.service.ISysPermissionService;
import com.honorfly.schoolsys.service.ISysUserService;
import com.honorfly.schoolsys.utils.AppConst;
import com.honorfly.schoolsys.utils.DateUtil;
import com.honorfly.schoolsys.utils.Result;
import com.honorfly.schoolsys.utils.ResultGenerator;
import com.honorfly.schoolsys.utils.service.Page;
import com.honorfly.schoolsys.utils.web.BaseController;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URLDecoder;
import java.util.*;


@RestController
@RequestMapping("/sys/user/")
public class SysPermissionAction extends BaseController {

	@Autowired
	private ISysPermissionService sysPermissionService;

	@Autowired
	private ISysUserService sysUserService;


	@ApiOperation(value="用户导航")
	@ResponseBody
	@RequestMapping(value = "/nav",method = RequestMethod.POST)
	public Result nav() throws Exception{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		SessionUser sessionUser = (SessionUser) auth.getPrincipal();
		SessionUser redisUser = (SessionUser) redisUtil.get(AppConst.Redis_Session_Namespace+sessionUser.getId());
		List<SysPermission> permissions = redisUser.buttons;

		Collections.sort(permissions, new Comparator<SysPermission>() {
			@Override
			public int compare(SysPermission o1, SysPermission o2) {
				return o2.getId()>o1.getId()?-1:1; //降序
			}
		});
		List navs = new ArrayList();
		List<SysPermission> dics = sysPermissionService.getPermissionsByParentId(permissions,0L);
		for(SysPermission dd:dics){
			Map navlevel1 = new HashMap();
			navlevel1.put("name",dd.getName());
			navlevel1.put("parentId",dd.getParentId());
			navlevel1.put("id",dd.getId());
			navlevel1.put("component",dd.getComponent());
			navlevel1.put("redirect",dd.getRedirect());
			Map metanavlevel1 = new HashMap();
			metanavlevel1.put("title",dd.getTitle());
			metanavlevel1.put("icon",dd.getIcon());
			metanavlevel1.put("show",dd.getShow());
			navlevel1.put("meta",metanavlevel1);
			navs.add(navlevel1);
			dics = sysPermissionService.getPermissionsByParentId(permissions,dd.getId());
			for(SysPermission d:dics){
				Map navlevel2 = new HashMap();
				navlevel2.put("name",d.getName());
				navlevel2.put("parentId",d.getParentId());
				navlevel2.put("id",d.getId());
				navlevel2.put("component",d.getComponent());
				Map metanavlevel2 = new HashMap();
				metanavlevel2.put("title",d.getTitle());
				metanavlevel2.put("icon",d.getIcon());
				metanavlevel2.put("show",d.getShow());
				metanavlevel2.put("hideHeader",false);
				metanavlevel2.put("hideChildren",true);
				navlevel2.put("meta",metanavlevel2);
				boolean flag = false;
				dics = sysPermissionService.getPermissionsByParentId(permissions,d.getId());
				for(SysPermission buttion:dics){
					flag = true;
					Map navlevel3 = new HashMap();
					navlevel3.put("name",buttion.getName());
					navlevel3.put("parentId",buttion.getParentId());
					navlevel3.put("id",buttion.getId());
					navlevel3.put("component",buttion.getComponent());
					//nav.put("redirect",d.getRedirect());
					navlevel3.put("path",buttion.getRedirect());
					Map metanavlevel3 = new HashMap();
					metanavlevel3.put("title",buttion.getTitle());
					metanavlevel3.put("icon",buttion.getIcon());
					metanavlevel3.put("show",buttion.getShow());
					navlevel3.put("meta",metanavlevel3);
					navs.add(navlevel3);
				}
				//如果有第三级则 第二级加 redirect
				if(flag){
					navlevel2.put("redirect",d.getRedirect());
				}
				navs.add(navlevel2);
			}
		}


		com.alibaba.fastjson.JSONArray json = new JSONArray(navs);
		System.out.println("xxxxxxx----"+json);
		return ResultGenerator.genSuccessResult(navs);
	}

	@ApiOperation(value="用户信息")
	@ResponseBody
	@RequestMapping(value = "/info",method = RequestMethod.POST)
	public Result info() throws Exception{
		SessionUser sessionUser = this.getRedisSession();
		Map role = new HashMap();
		role.put("id","admin");
		role.put("name","管理员");
		role.put("describe","拥有所有权限");
		role.put("creatorId","system");
		role.put("createTime","1497160610259");
		role.put("deleted",0);
		role.put("status",1);

		List permissions = new ArrayList();
		Map pession = new HashMap();
		pession.put("roleId","admin");
		pession.put("permissionId","dashboard");
		pession.put("permissionName","仪表盘");

		List actions = new ArrayList();
		Map action = new HashMap();
		action.put("action","add");
		action.put("defaultCheck",false);
		action.put("describe","新增");
		actions.add(action);
		action = new HashMap();
		action.put("action","query");
		action.put("defaultCheck",false);
		action.put("describe","查询");
		actions.add(action);

		pession.put("actions",actions);
		pession.put("actionEntitySet",actions);

		permissions.add(pession);

		role.put("permissions",permissions);

		Map userInfo = new HashMap();
		userInfo.put("name",sessionUser.getRealName());
		userInfo.put("token","4291d7da9005377ec9aec4a71ea837f");
		userInfo.put("avatar","https://gw.alipayobjects.com/zos/rmsportal/jZUIxmJycoymBprLOUbT.png");
		userInfo.put("status","1");
		userInfo.put("username",sessionUser.getUsername());
		userInfo.put("roleId","admin");
		userInfo.put("role",role);

		return ResultGenerator.genSuccessResult(userInfo);
	}




	@ApiOperation(value="用戶注销")
	@ResponseBody
	@RequestMapping(value = "/logout",method = RequestMethod.POST)
	public Result logout(HttpServletResponse response) throws Exception{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		SessionUser sessionUser = (SessionUser) auth.getPrincipal();
		redisUtil.del(AppConst.Redis_Session_Namespace+sessionUser.getId());
		if (auth != null){
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return ResultGenerator.genSuccessResult();
    }

	@ApiOperation(value="保存操作URL信息")
	@ResponseBody
	@RequestMapping(value = "/saveSysPermission",method = RequestMethod.POST)
	public Result saveSysPermission(@Valid SysPermissionForm sysPermissionForm, BindingResult bindingResult) throws Exception{
		if(bindingResult.hasErrors()){
			return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
		}
		SysPermission sysPermission = new SysPermission();
		BeanUtils.copyProperties(sysPermissionForm,sysPermission);
		sysPermissionService.save(sysPermission);
		return ResultGenerator.genSuccessResult();
	}

	@ApiOperation(value="修改操作URL信息")
	@ResponseBody
	@RequestMapping(value = "/editSysPermission",method = RequestMethod.POST)
	public Result editSysPermission(@Valid SysPermissionForm sysPermissionForm, BindingResult bindingResult) throws Exception{
		if(bindingResult.hasErrors()){
			return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
		}
		SysPermission sysPermission = sysPermissionService.getById(SysPermission.class, sysPermissionForm.getId());
		if(sysPermission==null){
			return ResultGenerator.genFailResult(AppConst.NOFIND_ERR_MSG);
		}
		BeanUtils.copyProperties(sysPermissionForm,sysPermission);
		sysPermissionService.save(sysPermission);
		return ResultGenerator.genSuccessResult();
	}

	@ApiOperation(value="通过id删除")
	@ResponseBody
	@RequestMapping(value = "/deleteSysPermission",method = RequestMethod.POST)
	public Result deleteSysPermission(Long id) throws Exception{
		if(id==null||id<1){
			return ResultGenerator.genFailResult("ID 不能为空！");
		}
		SysPermission dd = sysPermissionService.getById(SysPermission.class, id);
		if(dd==null){
			return ResultGenerator.genFailResult(AppConst.NOFIND_ERR_MSG);
		}
		List<SysPermission> dics = sysPermissionService.listByParentId(dd.getId());
		if(dics!=null){
			for(SysPermission d:dics){
				List<SysPermission> dicsc = sysPermissionService.listByParentId(d.getId());
				if(dicsc!=null){
					for(SysPermission buttion:dicsc){
						sysPermissionService.delPermission(buttion.getId());
						sysPermissionService.delete(SysPermission.class, buttion.getId());
					}
					sysPermissionService.delPermission(d.getId());
					sysPermissionService.delete(SysPermission.class, d.getId());
				}
			}
			sysPermissionService.delPermission(dd.getId());
			sysPermissionService.delete(SysPermission.class, dd.getId());
		}
		return ResultGenerator.genSuccessResult();
 	}

	@ApiOperation(value="查询子节点")
	@ResponseBody
	@RequestMapping(value = "/listByPage3",method = RequestMethod.POST)
	public Result listByPage3() throws Exception{
		List<Map> list = new ArrayList<Map>();
		List<SysPermission> dics = sysPermissionService.queryParent();
		for(SysPermission dd:dics){
			Map<String,Object> root = new HashMap<String,Object>();
			root.put("id", dd.getId());
			//root.put("parentId", dd.getParentId());
			root.put("title", dd.getTitle());
			root.put("text", dd.getTitle());
			root.put("status", dd.getStatus());
			root.put("url",dd.getRedirect());
			root.put("expanded", true);
			root.put("level", 0);
			root.put("leaf", false);
			root.put("icon",dd.getIcon());
			root.put("component",dd.getComponent());
			root.put("name",dd.getName());
			root.put("show",dd.getShow());
			dics = sysPermissionService.listByParentId(dd.getId());

			List<Map> chs2 = new ArrayList<Map>();
			for(SysPermission d:dics){
				Map rdm = new HashMap();
				rdm.put("id",d.getId());
				rdm.put("expanded", false);
				rdm.put("title",d.getTitle());
				rdm.put("text",d.getTitle());
				rdm.put("url",d.getRedirect());
				rdm.put("parentId",d.getParentId());
				rdm.put("status", d.getStatus());
				rdm.put("expanded", false);
				rdm.put("leaf", false);
				rdm.put("level", 1);
				rdm.put("icon",d.getIcon());
				rdm.put("component",d.getComponent());
				rdm.put("name",d.getName());
				rdm.put("show",d.getShow());
				List<Map> chs3 = new ArrayList<Map>();
				dics = sysPermissionService.listByParentId(d.getId());
				for(SysPermission buttion:dics){
					Map b = new HashMap();
					b.put("id",buttion.getId());
					b.put("expanded", false);
					b.put("title",buttion.getTitle());
					b.put("text",buttion.getTitle());
					b.put("url",buttion.getRedirect());
					b.put("parentId",buttion.getParentId());
					b.put("status", buttion.getStatus());
					b.put("leaf", true);
					b.put("level", 2);
					b.put("component",buttion.getComponent());
					b.put("name",buttion.getName());
					b.put("show",buttion.getShow());
					chs3.add(b);
				}
				if(chs3.size()>0){
					rdm.put("children", chs3);
				}
				rdm.put("nodes", chs3);
				chs2.add(rdm);
			}
			if(chs2.size()>0){
				root.put("children", chs2);
			}
			root.put("nodes", chs2);
			list.add(root);
		}
		return ResultGenerator.genSuccessResult(list);
	}

	@ApiOperation(value="角色id查询角色所有的权限")
	@ResponseBody
	@RequestMapping(value = "/roleListPermission",method = RequestMethod.POST)
	public Result roleListPermission(String roleId) throws Exception{
		List<Map<String,Object>> userpers = new ArrayList<Map<String,Object>>();
		if(!StringUtils.isBlank(roleId)){
			userpers = sysPermissionService.rolePermissionByRoleID(roleId);
		}
		List checkeds = new ArrayList();
		List<Map> list = new ArrayList<Map>();
		List<SysPermission> dics = sysPermissionService.queryParent();
		for(SysPermission dd:dics){
			Map<String,Object> root = new HashMap<String,Object>();
			root.put("id", dd.getId());
			root.put("parentId", dd.getParentId());
			root.put("name", dd.getTitle());
			root.put("status", dd.getStatus());
			root.put("expanded", false);
			boolean isExsitlevel1 = isExit(userpers,dd.getId());
			root.put("checked", isExsitlevel1);
			dics = sysPermissionService.listByParentId(dd.getId());
			if (isExsitlevel1&&dics.size()<1){
				checkeds.add(dd.getId());
			}
			List<Map> chs2 = new ArrayList<Map>();
			for(SysPermission d:dics){
				Map rdm = new HashMap();
				rdm.put("id",d.getId());
				rdm.put("expanded", false);
				rdm.put("name",d.getTitle());
				rdm.put("url",d.getRedirect());
				rdm.put("parentId",d.getParentId());
				rdm.put("status", d.getStatus());
				boolean isExsitlevel2 = isExit(userpers,d.getId());
				rdm.put("checked", isExsitlevel2);
				rdm.put("leaf", false);
				rdm.put("iconCls", "a");
				List<Map> chs3 = new ArrayList<Map>();
				dics = sysPermissionService.listByParentId(d.getId());
				if (isExsitlevel2&&dics.size()<1){
					checkeds.add(d.getId());
				}
				for(SysPermission buttion:dics){
					Map b = new HashMap();
					b.put("id",buttion.getId());
					b.put("expanded", true);
					b.put("name",buttion.getTitle());
					b.put("url",buttion.getRedirect());
					b.put("parentId",buttion.getParentId());
					b.put("status", buttion.getStatus());
					boolean isExsitlevel3 = isExit(userpers,buttion.getId());
					b.put("checked", isExsitlevel3);
					b.put("leaf", true);
					chs3.add(b);
					if (isExsitlevel3){
						checkeds.add(buttion.getId());
					}

				}
				rdm.put("children", chs3);
				chs2.add(rdm);
			}
			root.put("children", chs2);
			list.add(root);
		}


		/*checkeds.add(7);
		checkeds.add(10);
		checkeds.add(11);*/



		Map result = new HashMap();
		result.put("treeData",list);
		result.put("permissions",checkeds);

		return ResultGenerator.genSuccessResult(result);
    }

	private boolean isExit(List<Map<String,Object>> userper,Long id){
		if(userper!=null){
			for(Map<String,Object> up:userper){
				if((up.get("permission_id")+"").equals(id+"")){
					return true;
				}
			}
		}
		return false;
	}




	@Autowired
	ISchoolManagerService schoolManagerService;
	@ApiOperation(value="角色id查询")
	@ResponseBody
	@RequestMapping(value = "/saveRole",method = RequestMethod.POST)
   	public Result saveRole(@Valid SysRoleForm sysRoleForm, BindingResult bindingResult) throws Exception {
		if(bindingResult.hasErrors()){
			System.out.println(bindingResult.getFieldError().getDefaultMessage());
			return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
		}
		SysRole sysRole = null;
		if(sysRoleForm.getId()<1){
			sysRole = new SysRole();
			sysRoleForm.setId(null);
		}else{
			sysRole = sysPermissionService.getById(SysRole.class,sysRoleForm.getId());
		}
		BeanUtils.copyProperties(sysRoleForm,sysRole);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		SessionUser sessionUser = (SessionUser) auth.getPrincipal();
		SessionUser redisSession = (SessionUser) redisUtil.get(AppConst.Redis_Session_Namespace+sessionUser.getId());
		sysRole.setAdminId(redisSession.getAdminId());
		if (sysRoleForm.getSelectType()==2){//选择是部分
			List<School> schools = new ArrayList<School>();
			if(!StringUtils.isBlank(sysRoleForm.getSchoolIdStr())){
				schools = schoolManagerService.schoolList(redisSession.getAdminId(),sysRoleForm.getSchoolIdStr());
			}
			sysRole.getSchools().clear();
			sysRole.getSchools().addAll(schools);
		}else {//选择的是全部
			List<School> schools = schoolManagerService.schoolList(redisSession.getAdminId());
			sysRole.getSchools().clear();
			sysRole.getSchools().addAll(schools);
		}
		sysPermissionService.save(sysRole);
		return ResultGenerator.genSuccessResult();
    }


	@ApiOperation(value="角色id查询")
	@ResponseBody
	@RequestMapping(value = "/queryRole",method = RequestMethod.POST)
	public Result queryRole(@RequestBody @Valid IDForm idForm, BindingResult bindingResult) throws Exception {
		if(bindingResult.hasErrors()){
			return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
		}
		Map ret = new HashMap();
		SysRole dd= (SysRole)sysPermissionService.getById(SysRole.class, Long.valueOf(idForm.getId()));
		StringBuffer sysroles = new StringBuffer();
		List<Map<String,Object>> userpers = sysPermissionService.rolePermissionByRoleID(idForm.getId()+"");
		for(Map<String,Object> up:userpers){
			String v = up.get("permission_id").toString();
			sysroles.append(v+",");
		}
		ret.put("syspermissions", sysroles.toString());
		ret.put("sysrole", dd);

		return ResultGenerator.genSuccessResult(ret);
	}

	@ApiOperation(value="角色id查询")
	@ResponseBody
	@RequestMapping(value = "/queryRoleDetail",method = RequestMethod.POST)
	public Result queryRoleDetail(@Valid IDForm idForm, BindingResult bindingResult) throws Exception {
		if(bindingResult.hasErrors()){
			return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
		}
		SysRole sysRole= sysPermissionService.getById(SysRole.class, Long.valueOf(idForm.getId()));
		Map result = new HashMap();
		result.put("id",sysRole.getId());
		result.put("selectType",sysRole.getSelectType());
		List roles = new ArrayList();
		for(School school:sysRole.getSchools()){
			roles.add(school.getId());
		}
		result.put("schools",roles);
		return ResultGenerator.genSuccessResult(result);


	}




	@ApiOperation(value="角色id查询")
	@ResponseBody
	@RequestMapping(value = "/rolePageList",method = RequestMethod.POST)
	public Result roleListByPage(String name, Boolean invalid,int pageNo,int pageSize) throws Exception{
		Map<String,String> search = new HashMap<String,String>();
		if(!StringUtils.isBlank(name)){
			name = URLDecoder.decode(name,"UTF-8");
			search.put("name",name);
		}
		if(invalid!=null){
			search.put("invalid",invalid+"");
		}
		Page page = sysUserService.roleListByPage(search, pageNo, pageSize);
		List<Map> list = new ArrayList<Map>();
		for(Object sm:page.getData()){
			Map mo = new HashMap();
			mo.put("id", ((SysRole)sm).getId());
			mo.put("key", ((SysRole)sm).getId());
			mo.put("roleName", ((SysRole)sm).getRoleName());
			mo.put("createTime", ((SysRole)sm).getCreatedDate()==null?"": DateUtil.getStrYMDHMByDate(((SysRole)sm).getCreatedDate()));
			mo.put("invalid",((SysRole) sm).getInvalid());
			list.add(mo);

		}
		page.setData(list);
		return ResultGenerator.genSuccessResult(page);
	}



	@ApiOperation(value="角色授权")
	@ResponseBody
	@RequestMapping(value = "/settingRolePermission",method = RequestMethod.POST)
	public Result settingRolePermission(long roleId,String permissionsIdStr) throws Exception{
		if(StringUtils.isBlank(permissionsIdStr)){
			return ResultGenerator.genFailResult("权限集不能为空");
		}
		SysRole sysRole = sysUserService.getById(SysRole.class,roleId);
		if(sysRole==null){
			return ResultGenerator.genFailResult("没有对应的角色");
		}
		List syspermission = sysPermissionService.listSysPermissionByIDString(permissionsIdStr);
		sysRole.getPermissions().clear();
		sysRole.getPermissions().addAll(syspermission);
		sysUserService.save(sysRole);
		return ResultGenerator.genSuccessResult();
	}

	@ApiOperation(value="批量修改角色")
	@ResponseBody
	@RequestMapping(value = "/editRoleBatch",method = RequestMethod.POST)
	public Result editRoleBatch(String idsStr,int invalid) throws Exception {
		if(StringUtils.isBlank(idsStr)){
			return ResultGenerator.genFailResult("idsStr不能为空！");
		}
		sysPermissionService.editRoleBatch(idsStr,invalid);
		return ResultGenerator.genSuccessResult();
	}


	@ApiOperation(value="用户分页列表")
	@ResponseBody
	@RequestMapping(value = "/userPageList",method = RequestMethod.POST)
	public Result userPageList(String mobile,
							   String realName,
							   String userName,
							   Boolean invalid,
							   int pageNo,int pageSize) throws Exception{
		Map<String,String> search = new HashMap<String,String>();
		if(!StringUtils.isBlank(userName)){
			userName = URLDecoder.decode(userName,"UTF-8");
			search.put("userName",userName);
		}
		if(!StringUtils.isBlank(realName)){
			realName = URLDecoder.decode(realName,"UTF-8");
			search.put("realName",realName);
		}
		if(!StringUtils.isBlank(mobile)){
			mobile = URLDecoder.decode(mobile,"UTF-8");
			search.put("mobile",mobile);
		}
		if(invalid!=null){
			search.put("invalid",invalid+"");
		}
		Page page = sysUserService.userPageList(search, pageNo, pageSize);
		List<Map> list = new ArrayList<Map>();
		for(Object sm:page.getData()){
			Map mo = new HashMap();
			mo.put("id", ((SysUser)sm).getId());
			mo.put("realName", ((SysUser)sm).getRealName());
			mo.put("userName", ((SysUser)sm).getUserName());
			mo.put("mobile", ((SysUser)sm).getMobile());
			mo.put("position", ((SysUser)sm).getPosition());
			mo.put("createTime", ((SysUser)sm).getCreatedDate()==null?"": DateUtil.getStrYMDHMByDate(((SysUser)sm).getCreatedDate()));
			mo.put("invalid",((SysUser) sm).getInvalid());
			list.add(mo);
		}
		page.setData(list);
		return ResultGenerator.genSuccessResult(page);
	}

	@ApiOperation(value="角色列表列表")
	@ResponseBody
	@RequestMapping(value = "/roleList",method = RequestMethod.POST)
	public Result roleList() throws Exception{
		List roles = sysUserService.roleList();
		List<Map> list = new ArrayList<Map>();
		for(Object sm:roles){
			Map mo = new HashMap();
			mo.put("id", ((SysRole)sm).getId());
			mo.put("realName", ((SysRole)sm).getRoleName());
			list.add(mo);
		}
		return ResultGenerator.genSuccessResult(list);
	}


	@ApiOperation(value="保存用户信息")
	@ResponseBody
	@RequestMapping(value = "/saveSysUser",method = RequestMethod.POST)
	public Result saveSysUser(
						   String userName,
						   String password,
						   String realName,
						   String empNumber,
						   String position,
						   Boolean invalid,
						   Integer status,
						   String newRoles,
						   String department,
						   String mobile) throws Exception {
		if(StringUtils.isBlank(userName)||
				StringUtils.isBlank(realName)||
				StringUtils.isBlank(position)){
			return ResultGenerator.genFailResult("idsStr不能为空！");
		}
		SysUser  dd = new SysUser();
		if(status!=null){
			dd.setStatus(status);
		}
		if(invalid!=null){
			dd.setInvalid(invalid);
		}
		SysUser dd2 = sysUserService.queryUserByName(userName);
		if(dd2!=null){
			return ResultGenerator.genFailResult("用户名已被占用！");
		}
		if(!StringUtils.isBlank(userName)){
			dd.setUserName(userName);
		}
		if(!StringUtils.isBlank(password)){
			password = new BCryptPasswordEncoder().encode(password);
			dd.setPassword(password);
		}
		if(!StringUtils.isBlank(realName)){
			dd.setRealName(realName);
		}
		if(!StringUtils.isBlank(empNumber)){
			dd.setEmpNumber(empNumber);
		}
		if(!StringUtils.isBlank(department)){
			dd.setDepartment(department);
		}
		if(!StringUtils.isBlank(mobile)){
			dd.setMobile(mobile);
		}
		if(!StringUtils.isBlank(position)){
			dd.setPosition(position);
		}
		if(!StringUtils.isBlank(newRoles)){
			List<SysRole> roles = sysUserService.listRolesByIDString(newRoles);
			dd.getRoles().clear();
			dd.getRoles().addAll(roles);
		}else{
			dd.getRoles().clear();
		}
		dd.setAdminId(getAdminId());
		sysUserService.save(dd);
		return ResultGenerator.genSuccessResult();
	}

	@ApiOperation(value="修改用户信息")
	@ResponseBody
	@RequestMapping(value = "/editSysUser",method = RequestMethod.POST)
	public Result editSysUser(
			long id,
			String userName,
			String password,
			String realName,
			String empNumber,
			String position,
			Boolean invalid,
			Integer status,
			String newRoles,
			String department,
			String mobile) throws Exception {
		if(StringUtils.isBlank(userName)||
				StringUtils.isBlank(realName)||
				StringUtils.isBlank(position)){
			return ResultGenerator.genFailResult("idsStr不能为空！");
		}
		SysUser  dd = sysUserService.getById(SysUser.class,id);
		if(status!=null){
			dd.setStatus(status);
		}
		if(invalid!=null){
			dd.setInvalid(invalid);
		}
		SysUser dd2 = sysUserService.queryUserByName(userName);
		if(dd2!=null&&dd2.getId().longValue()!=dd.getId().longValue()){
			return ResultGenerator.genFailResult("用户名已被占用！");
		}
		if(!StringUtils.isBlank(userName)){
			dd.setUserName(userName);
		}
		if(!StringUtils.isBlank(password)){
			password = new BCryptPasswordEncoder().encode(password);
			dd.setPassword(password);
		}
		if(!StringUtils.isBlank(realName)){
			dd.setRealName(realName);
		}
		if(!StringUtils.isBlank(empNumber)){
			dd.setEmpNumber(empNumber);
		}
		if(!StringUtils.isBlank(department)){
			dd.setDepartment(department);
		}
		if(!StringUtils.isBlank(mobile)){
			dd.setMobile(mobile);
		}
		if(!StringUtils.isBlank(position)){
			dd.setPosition(position);
		}
		if(!StringUtils.isBlank(newRoles)){
			List<SysRole> roles = sysUserService.listRolesByIDString(newRoles);
			dd.getRoles().clear();
			dd.getRoles().addAll(roles);
		}else{
			dd.getRoles().clear();
		}
		sysUserService.save(dd);
		return ResultGenerator.genSuccessResult();
	}

	@ApiOperation(value="删除系统用户")
	@ResponseBody
	@RequestMapping(value = "/deleteSysUser",method = RequestMethod.POST)
	public Result deleteSysUser(long id) throws Exception {
		sysUserService.delete(SysUser.class,id);
		return ResultGenerator.genSuccessResult();
	}

	@ApiOperation(value="系统用户详情")
	@ResponseBody
	@RequestMapping(value = "/detailSysUser",method = RequestMethod.POST)
	public Result detailSysUser(long id) throws Exception {
		SysUser sysUser = sysUserService.getById(SysUser.class,id);
		Map result = new HashMap();
		result.put("id",sysUser.getId());
		List roles = new ArrayList();
		for(SysRole sysRole:sysUser.getRoles()){
			roles.add(sysRole.getId());
		}
		result.put("roles",roles);
		return ResultGenerator.genSuccessResult(result);
	}


	@ApiOperation(value="获取验证码")
	@ResponseBody
	@RequestMapping(value = "/getCaptcha",method = RequestMethod.POST)
	public Result getCaptcha(String mobile) throws Exception {
		if(StringUtils.isBlank(mobile)){
			return ResultGenerator.genFailResult("手机号码不能为空");
		}
		String code = "";
		while (StringUtils.isBlank(code)||code.contains("4")){
			code = String.valueOf((int)((Math.random()*9+1)*1000));
		}
		if(StringUtils.isNotEmpty(mobile)){
			redisUtil.set(AppConst.Redis_Captcha_Namespace+mobile,code);
		}
		return ResultGenerator.genSuccessResult(code);
	}
}
