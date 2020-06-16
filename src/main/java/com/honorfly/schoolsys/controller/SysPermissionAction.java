

package com.honorfly.schoolsys.controller;


import com.alibaba.fastjson.JSONArray;
import com.honorfly.schoolsys.entry.SysPermission;
import com.honorfly.schoolsys.entry.SysRole;
import com.honorfly.schoolsys.entry.SysUser;
import com.honorfly.schoolsys.form.IDForm;
import com.honorfly.schoolsys.form.SysPermissionForm;
import com.honorfly.schoolsys.form.SysRoleForm;
import com.honorfly.schoolsys.form.UserLoginForm;
import com.honorfly.schoolsys.service.ISysPermissionService;
import com.honorfly.schoolsys.service.ISysUserService;
import com.honorfly.schoolsys.utils.AppConst;
import com.honorfly.schoolsys.utils.Result;
import com.honorfly.schoolsys.utils.ResultGenerator;
import com.honorfly.schoolsys.utils.web.BaseController;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;


@Controller
@RequestMapping("/sys/user/")
public class SysPermissionAction extends BaseController {

	@Autowired
	private ISysPermissionService sysPermissionService;

	@Autowired
	private ISysUserService sysUserService;

	@ApiOperation(value="用戶登錄")
	@ResponseBody
	@RequestMapping(value = "/userLogin",method = RequestMethod.POST)
	public Result userLogin(@RequestBody @Valid UserLoginForm userLoginForm, BindingResult bindingResult) throws Exception{
		/*if(bindingResult.hasErrors()){
			return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
		}
		HttpSession session = request.getSession();
		String passWord = DigestUtils.md5DigestAsHex(userLoginForm.getPassword().getBytes());
		SysUser user = sysPermissionService.userLogin(userLoginForm.getUserName(), passWord);
		if(user==null){
			return ResultGenerator.genFailResult(ResultCode.AUTH_FAIL,"账号密码错误");
		}
		if(passWord.endsWith(user.getPassword())){
			for(SysRole role:user.getRoles()){
				if(role.getPermissions()!=null&&role.getPermissions().size()>0){
					role.buttons.addAll(role.getPermissions());
				}
			}
			sysPermissionService.save(user);
			session.setAttribute(AppConst.USER_KEY, user);
		}*/
		Map responseData = new HashMap();
		responseData.put("name","邓忠明");
		responseData.put("token","4291d7da9005377ec9aec4a71ea837f");
		responseData.put("avatar","https://gw.alipayobjects.com/zos/rmsportal/jZUIxmJycoymBprLOUbT.png");
		responseData.put("status","1");
		responseData.put("username","admin");
		responseData.put("roleId","admin");
		return ResultGenerator.genSuccessResult(responseData);
    }
/*
	@ApiOperation(value="用户导航")
	@ResponseBody
	@RequestMapping(value = "/nav",method = RequestMethod.POST)
	public Result nav() throws Exception{
		List navs = new ArrayList();
		Map nav = new HashMap();
		nav.put("name","dashboard");
		nav.put("parentId",0);
		nav.put("id",1);
		nav.put("component","RouteView");
		nav.put("redirect","/dashboard/workplace");
		Map meta = new HashMap();
		meta.put("title","仪表盘");
		meta.put("icon","dashboard");
		meta.put("show",true);
		nav.put("meta",meta);
		navs.add(nav);

		nav = new HashMap();
		nav.put("name","workplace");
		nav.put("parentId",1);
		nav.put("id",7);
		nav.put("component","Workplace");
		meta = new HashMap();
		meta.put("title","工作台");
		meta.put("show",true);
		nav.put("meta",meta);
		navs.add(nav);

		nav = new HashMap();
		nav.put("name","sysusermanager");
		nav.put("parentId",0);
		nav.put("id",20028);
		nav.put("component","RouteView");
		nav.put("redirect","/sysusermanager/perssion");
		meta = new HashMap();
		meta.put("title","用户管理");
		meta.put("icon","user");
		meta.put("show",true);
		nav.put("meta",meta);
		navs.add(nav);

		nav = new HashMap();
		nav.put("name","perssion");
		nav.put("parentId",20028);
		nav.put("id",20029);
		nav.put("component","PersionManage");
		meta = new HashMap();
		meta.put("title","权限管理");
		meta.put("show",true);
		nav.put("meta",meta);
		navs.add(nav);
		com.alibaba.fastjson.JSONArray json = new JSONArray(navs);
		System.out.println(json);
		return ResultGenerator.genSuccessResult(navs);
	}*/

	@ApiOperation(value="用户导航")
	@ResponseBody
	@RequestMapping(value = "/nav",method = RequestMethod.POST)
	public Result nav() throws Exception{
		List navs = new ArrayList();
		List<SysPermission> dics = sysPermissionService.queryParent();
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
			dics = sysPermissionService.listByParentId(dd.getId());
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
				dics = sysPermissionService.listByParentId(d.getId());
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
		System.out.println(json);
		return ResultGenerator.genSuccessResult(navs);
	}

	@ApiOperation(value="用户信息")
	@ResponseBody
	@RequestMapping(value = "/info",method = RequestMethod.POST)
	public Result info() throws Exception{

    /*const roleObj =

				{
						'id':'admin',
				name:管理员,
				describe:拥有所有权限,
				status:1,
				creatorId:system,
				createTime:1497160610259,
				deleted:0,
				permissions: [{
			roleId:
			admin,
					permissionId:dashboard,
					permissionName:仪表盘,
					actions: [{
				"action":"add", "defaultCheck":false, "describe":"新增"
			},{
				"action":"query", "defaultCheck":false, "describe":"查询"
			},{
				"action":"get", "defaultCheck":false, "describe":"详情"
			},{
				"action":"update", "defaultCheck":false, "describe":"修改"
			},{
				"action":"delete", "defaultCheck":false, "describe":"删除"
			}],
			actionEntitySet: [{
				action:
				add,
						describe:新增,
						defaultCheck:false
			},{
				action:
				query,
						describe:查询,
						defaultCheck:false
			},{
				action:
				get,
						describe:详情,
						defaultCheck:false
			},{
				action:
				update,
						describe:修改,
						defaultCheck:false
			},{
				action:
				delete,
						describe:删除,
						defaultCheck:false
			}],
			actionList:null,
					dataAccess:null
		}]
	}*/

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
		userInfo.put("name","邓忠明");
		userInfo.put("token","4291d7da9005377ec9aec4a71ea837f");
		userInfo.put("avatar","https://gw.alipayobjects.com/zos/rmsportal/jZUIxmJycoymBprLOUbT.png");
		userInfo.put("status","1");
		userInfo.put("username","admin");
		userInfo.put("roleId","admin");
		userInfo.put("role",role);

		return ResultGenerator.genSuccessResult(userInfo);
	}


	public static void main(String[] args) {
		List navs = new ArrayList();
		Map nav = new HashMap();
		nav.put("name","dashboard");
		nav.put("parentId",0);
		nav.put("id",1);
		nav.put("component","RouteView");
		nav.put("redirect","/dashboard/workplace");
		Map meta = new HashMap();
		meta.put("title","仪表盘");
		meta.put("icon","dashboard");
		meta.put("show",true);
		nav.put("meta",meta);
		navs.add(nav);

		nav = new HashMap();
		nav.put("name","workplace");
		nav.put("parentId",1);
		nav.put("id",7);
		nav.put("component","Workplace");
		meta = new HashMap();
		meta.put("title","工作台");
		meta.put("show",true);
		nav.put("meta",meta);
		navs.add(nav);

		nav = new HashMap();
		nav.put("name","sysusermanager");
		nav.put("parentId",0);
		nav.put("id",20028);
		nav.put("component","RouteView");
		nav.put("redirect","/sysusermanager/perssion");
		meta = new HashMap();
		meta.put("title","系统用户管理");
		meta.put("icon","user");
		meta.put("show",true);
		nav.put("meta",meta);
		navs.add(nav);

		nav = new HashMap();
		nav.put("name","perssion");
		nav.put("parentId",20028);
		nav.put("id",20029);
		nav.put("component","Workplace");
		meta = new HashMap();
		meta.put("title","权限管理");
		meta.put("show",true);
		nav.put("meta",meta);
		navs.add(nav);

		com.alibaba.fastjson.JSONArray json = new JSONArray(navs);
		System.out.println(json);
	}

	@ApiOperation(value="用戶注销")
	@ResponseBody
	@RequestMapping(value = "/logout",method = RequestMethod.POST)
	public Result logout() throws Exception{
		HttpSession session = request.getSession();
		SysUser user = (SysUser)session.getAttribute(AppConst.USER_KEY);
		session.removeAttribute(AppConst.USER_KEY);
		return ResultGenerator.genSuccessResult();
    }

	@ApiOperation(value="用戶权限列表")
	@ResponseBody
	@RequestMapping(value = "/userPermission",method = RequestMethod.POST)
	public Result userPermission() throws Exception{
		HttpSession session = request.getSession();
		SysUser user = (SysUser)session.getAttribute(AppConst.USER_KEY);
		Set<SysPermission> allPermissions = new HashSet<SysPermission>();
		Set<SysRole> roles = user.getRoles();
		for(SysRole r:roles){
			allPermissions.addAll(r.getPermissions());
		}
		List<Map> ret = new ArrayList<Map>();
		for(SysPermission p:allPermissions){
			if(p.getParentId()==-1){
				Map<String,Object> n = new HashMap<String,Object>();
				n.put("text", p.getTitle());
				n.put("mid", p.getId()+"");
				n.put("cls", "folder");
				n.put("leaf", false);
				n.put("expanded", true);
				List<Map> list = this.childrens(allPermissions, p.getId());
				n.put("children", list);
				ret.add(n);
			}
		}
		return ResultGenerator.genSuccessResult(ret);
    }


	@ApiOperation(value="页面按钮集是否有权限")
	@ResponseBody
	@RequestMapping(value = "/existsInUserPermissions",method = RequestMethod.POST)
	public Result existsInUserPermissions(String requestpath) throws Exception{
		ArrayList ret = new ArrayList();
		HttpSession session = request.getSession();
		SysUser user = (SysUser)session.getAttribute(AppConst.USER_KEY);
		String[] reqs = requestpath.split(",");
		for(int i=0;i<reqs.length;i++){
		         String value = reqs[i];
		         if("白名单列表".indexOf(value)>-1){
		 			ret.add(value);
		 		}else{
		 			first:for(SysRole role:user.getRoles()){
		 				   sencond:for(SysPermission permission:role.buttons){
		 					 // System.out.println(requestpath+":---->"+permission.getRedirect());
		 					  if(!StringUtils.isBlank(permission.getRedirect())&&value.endsWith(permission.getRedirect())){
		 						  ret.add(value);
		 						  break first;
		 					  }
		 				   }
		 			   }
		 		}
		 }
		return ResultGenerator.genSuccessResult(ret);

    }

	@ApiOperation(value="用戶信息")
	@ResponseBody
	@RequestMapping(value = "/getUser",method = RequestMethod.POST)
	public Result getUser(String requestpath) throws Exception{
		HttpSession session = request.getSession();
		SysUser user = (SysUser)session.getAttribute(AppConst.USER_KEY);
		Map ret = new HashMap();
		ret.put("realName", user.getRealName());
		ret.put("userName", user.getUserName());
		return ResultGenerator.genSuccessResult(ret);
    }

    private List<Map> childrens(Set<SysPermission> ps,Long parentId){
    	List<Map> list = new ArrayList<Map>();
    	for(SysPermission p:ps){
    		if(p.getParentId().equals(parentId)){
    			Map<String,Object> n = new HashMap<String,Object>();
				n.put("text", p.getTitle());
				n.put("mid", p.getId()+"");
				n.put("cls", "file");
				n.put("glyph", "xf002@FontAwesome");
				n.put("leaf", true);
				n.put("url", p.getRedirect());
				n.put("children", new ArrayList<Map>());
				list.add(n);
    		}
    	}
    	return list;
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

	@ApiOperation(value="查询")
	@ResponseBody
	@RequestMapping(value = "/query",method = RequestMethod.POST)
	public Result query(@RequestBody @Valid IDForm IDForm, BindingResult bindingResult) throws Exception{
		if(bindingResult.hasErrors()){
			return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
		}
		SysPermission dd= (SysPermission)sysPermissionService.getById(SysPermission.class, Long.valueOf(IDForm.getId()));
		SysPermission parent= null;
		if(dd.getParentId()!=null||dd.getParentId()!=-1){
			parent= (SysPermission)sysPermissionService.getById(SysPermission.class, dd.getParentId());
		}
		if(!StringUtils.isBlank(dd.getRedirect())&&dd.getRedirect().endsWith(".action")){
			dd.setIsLeaf(true);
		}else{
			dd.setIsLeaf(false);
		}
		dd.setParent(parent);
		return ResultGenerator.genSuccessResult(dd);
	}

	@ApiOperation(value="查")
	@ResponseBody
	@RequestMapping(value = "/queryParent",method = RequestMethod.POST)
	public Result queryParent() throws Exception{
		List<SysPermission> dics = sysPermissionService.queryParent();
		SysPermission dd = new SysPermission();
		dd.setId(Long.valueOf(-1));
		dd.setTitle("根结点");
		dics.add(0, dd);
		return ResultGenerator.genSuccessResult(dics);
	}


	@ApiOperation(value="查询子节点")
	@ResponseBody
	@RequestMapping(value = "/querySon",method = RequestMethod.POST)
	public Result querySon(Long parentId) throws Exception{
		List<SysPermission>  dics = sysPermissionService.listByParentId(parentId);
		return ResultGenerator.genSuccessResult(dics);
	}


	@ApiOperation(value="查询子节点")
	@ResponseBody
	@RequestMapping(value = "/listByPage",method = RequestMethod.POST)
	public Result listByPage() throws Exception{
		List<Map> list = new ArrayList<Map>();
		List<SysPermission> dics = sysPermissionService.queryParent();
		for(SysPermission dd:dics){
			Map<String,Object> root = new HashMap<String,Object>();
			root.put("id", dd.getId());
			//root.put("parentId", dd.getParentId());
			root.put("name", dd.getTitle());
			root.put("text", dd.getTitle());
			root.put("status", dd.getStatus());
			root.put("url",dd.getRedirect());
			root.put("expanded", true);
			root.put("level", 0);
			root.put("leaf", false);
			dics = sysPermissionService.listByParentId(dd.getId());

			List<Map> chs2 = new ArrayList<Map>();
			for(SysPermission d:dics){
				Map rdm = new HashMap();
				rdm.put("id",d.getId());
				rdm.put("expanded", false);
				rdm.put("name",d.getTitle());
				rdm.put("text",d.getTitle());
				rdm.put("url",d.getRedirect());
				rdm.put("parentId",d.getParentId());
				rdm.put("status", d.getStatus());
				rdm.put("expanded", false);
				rdm.put("leaf", false);
				rdm.put("level", 1);
				List<Map> chs3 = new ArrayList<Map>();
				dics = sysPermissionService.listByParentId(d.getId());
				for(SysPermission buttion:dics){
					Map b = new HashMap();
					b.put("id",buttion.getId());
					b.put("expanded", false);
					b.put("name",buttion.getTitle());
					b.put("text",buttion.getTitle());
					b.put("url",buttion.getRedirect());
					b.put("parentId",buttion.getParentId());
					b.put("status", buttion.getStatus());
					b.put("leaf", true);
					b.put("level", 2);
					chs3.add(b);
				}
				rdm.put("children", chs3);
				rdm.put("nodes", chs3);
				chs2.add(rdm);
			}
			root.put("children", chs2);
			root.put("nodes", chs2);
			list.add(root);
		}
		return ResultGenerator.genSuccessResult(list);
    }


	@ApiOperation(value="查询子节点")
	@ResponseBody
	@RequestMapping(value = "/listByPage2",method = RequestMethod.POST)
	public Result listByPage2() throws Exception{
		List<Map> list = new ArrayList<Map>();
		List<SysPermission> dics = sysPermissionService.queryParent();
		for(SysPermission dd:dics){
			Map<String,Object> root = new HashMap<String,Object>();
			root.put("id", dd.getId());
			//root.put("parentId", dd.getParentId());
			root.put("name", dd.getTitle());
			root.put("text", dd.getTitle());
			root.put("url",dd.getRedirect());
			root.put("parent",dd.getParentId());
			root.put("isLeaf", false);
			root.put("level", 0);
			root.put("expanded", true);
			list.add(root);
			dics = sysPermissionService.listByParentId(dd.getId());

			for(SysPermission d:dics){
				Map rdm = new HashMap();
				rdm.put("id",d.getId());
				rdm.put("name",d.getTitle());
				rdm.put("text",d.getTitle());
				rdm.put("url",d.getRedirect());
				rdm.put("parent",d.getParentId());
				rdm.put("isLeaf", false);
				rdm.put("level", 1);
				rdm.put("expanded", true);
				list.add(rdm);
				dics = sysPermissionService.listByParentId(d.getId());
				for(SysPermission buttion:dics){
					Map b = new HashMap();
					b.put("id",buttion.getId());
					b.put("name",buttion.getTitle());
					b.put("text",buttion.getTitle());
					b.put("url",buttion.getRedirect());
					b.put("parent",buttion.getParentId());
					b.put("isLeaf", true);
					b.put("level", 2);
					b.put("expanded", true);
					list.add(b);
				}
			}
		}
		return ResultGenerator.genSuccessResult(list);
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

	@ApiOperation(value="角色id查询")
	@ResponseBody
	@RequestMapping(value = "/listPermission",method = RequestMethod.POST)
	public Result listPermission(String roleId) throws Exception{
		List<Map<String,Object>> userpers = new ArrayList<Map<String,Object>>();
		if(!StringUtils.isBlank(roleId)){
			userpers = sysPermissionService.rolePermissionByRoleID(roleId);
		}
		List<Map> list = new ArrayList<Map>();
		List<SysPermission> dics = sysPermissionService.queryParent();
		for(SysPermission dd:dics){
			Map<String,Object> root = new HashMap<String,Object>();
			root.put("id", dd.getId());
			root.put("parentId", dd.getParentId());
			root.put("name", dd.getTitle());
			root.put("status", dd.getStatus());
			root.put("expanded", false);
			root.put("checked", isExit(userpers,dd.getId()));
			dics = sysPermissionService.listByParentId(dd.getId());
			List<Map> chs2 = new ArrayList<Map>();
			for(SysPermission d:dics){
				Map rdm = new HashMap();
				rdm.put("id",d.getId());
				rdm.put("expanded", false);
				rdm.put("name",d.getTitle());
				rdm.put("url",d.getRedirect());
				rdm.put("parentId",d.getParentId());
				rdm.put("status", d.getStatus());
				rdm.put("checked", isExit(userpers,d.getId()));
				rdm.put("leaf", false);
				rdm.put("iconCls", "a");
				List<Map> chs3 = new ArrayList<Map>();
				dics = sysPermissionService.listByParentId(d.getId());
				for(SysPermission buttion:dics){
					Map b = new HashMap();
					b.put("id",buttion.getId());
					b.put("expanded", true);
					b.put("name",buttion.getTitle());
					b.put("url",buttion.getRedirect());
					b.put("parentId",buttion.getParentId());
					b.put("status", buttion.getStatus());
					b.put("checked", isExit(userpers,buttion.getId()));
					b.put("leaf", true);
					chs3.add(b);
				}
				rdm.put("children", chs3);
				chs2.add(rdm);
			}
			root.put("children", chs2);
			list.add(root);
		}
		return ResultGenerator.genSuccessResult(list);
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




	@ApiOperation(value="角色id查询")
	@ResponseBody
	@RequestMapping(value = "/saveRole",method = RequestMethod.POST)
   	public Result saveRole(@RequestBody @Valid SysRoleForm sysRoleForm, BindingResult bindingResult) throws Exception {
		if(bindingResult.hasErrors()){
			return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
		}
		SysRole sysRole = new SysRole();
		BeanUtils.copyProperties(sysRoleForm,sysRole);
		sysRole.setCreatedDate(new Date());
		if(!StringUtils.isBlank(sysRoleForm.getNewpermissions())){
			List<SysPermission> dics = sysPermissionService.listSysPermissionByIDString(sysRoleForm.getNewpermissions());
			sysRole.getPermissions().clear();
			sysRole.getPermissions().addAll(dics);
		}else{
			sysRole.getPermissions().clear();
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
}
