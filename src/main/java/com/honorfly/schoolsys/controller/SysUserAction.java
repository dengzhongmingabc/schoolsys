

package com.honorfly.schoolsys.controller;


import com.honorfly.schoolsys.utils.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/sys/test/")
public class SysUserAction extends BaseController {

/*
	@Autowired
	private ISysUserService sysUserService;



	@ApiOperation(value="用戶登錄")
	@ResponseBody
	@RequestMapping(value = "/roleListByPage",method = RequestMethod.POST)
	public Result roleListByPage(String search,int currentPage) throws Exception{
		if(!StringUtils.isBlank(search)){
			search = URLDecoder.decode(search,"UTF-8");
		}
		Page page = sysUserService.roleListByPage(search, currentPage, pageSize);
		List<Map> list = new ArrayList<Map>();
		for(Object sm:page.getResults()){
			Map mo = new HashMap();
			mo.put("id", ((SysRole)sm).getId());
			mo.put("name", ((SysRole)sm).getRoleName());
			mo.put("createTime", ((SysRole)sm).getCreatedDate()==null?"": DateUtil.getStrYMDHMByDate(((SysRole)sm).getCreatedDate()));
			mo.put("status", ((SysRole)sm).getStatus()==null||((SysRole)sm).getStatus().equals(Entity.Status.effective.name())?"有效":"无效");
			list.add(mo);
		}
		return ResultGenerator.genSuccessResult();
    }

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/userListByPage", method = {RequestMethod.GET, RequestMethod.POST})
	public String userListByPage(HttpServletRequest request, HttpServletResponse response,String search,int currentPage) {
		try {
			Map<String,String> where = new HashMap<String,String>();
			//加入代理区分
			HttpSession session = request.getSession();
			SysUser user = (SysUser)session.getAttribute(Constants.LOGIN_KEY);
			if("A0001".equals(user.getDepartment())){
				where.put("parentId",user.getDepartmentId()+"");
			}
			if(!StringUtils.isBlank(search)){
				search = URLDecoder.decode(search,"UTF-8");
				where.put("name",search);
			}
			Page page = sysUserService.userListByPage(where, currentPage, pageSize);
			List<Map> list = new ArrayList<Map>();
			for(Object sm:page.getResults()){
				Map mo = new HashMap();
				mo.put("id", ((SysUser)sm).getId());
				mo.put("userName", ((SysUser)sm).getUserName());
				mo.put("realName", ((SysUser)sm).getRealName());
				mo.put("empNumber", ((SysUser)sm).getEmpNumber());
				mo.put("department", ((SysUser)sm).getDepartment());
				mo.put("position", ((SysUser)sm).getPosition());
				mo.put("createTime", ((SysUser)sm).getCreatedDate()==null?"":DateUtil.getStrYMDHMByDate(((SysUser)sm).getCreatedDate()));
				mo.put("status", ((SysUser)sm).getStatus()==null||((SysUser)sm).getStatus().equals(Entity.Status.effective.name())?"有效":"无效");
				mo.put("proxyAdmin", ((SysUser)sm).getProxyAdmin()==null||((SysUser)sm).getProxyAdmin()==false?"否":"是");
				mo.put("mobileUser", ((SysUser)sm).getMobileUser()==null||((SysUser)sm).getMobileUser()==false?"否":"是");
				mo.put("balance", ((SysUser)sm).getBalance()==null?0.0:NumberUtil.formatDouble5(((SysUser)sm).getBalance()));
				mo.put("points", ((SysUser)sm).getPoints()==null?0:((SysUser)sm).getPoints());
				list.add(mo);
			}
			JSONHelper.returnInfo(JSONHelper.list2json(list,page.getTotalCount()+""));
			return NONE;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			JSONHelper.returnInfo(JSONHelper.failedInfo(Constants.failed_code,Constants.failed_msg));
			return NONE;
		}
    }


	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/sysUsreInfo", method = {RequestMethod.GET, RequestMethod.POST})
	public String sysUsreInfo(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String,String> where = new HashMap<String,String>();
			//加入代理区分
			HttpSession session = request.getSession();
			SysUser user = (SysUser)session.getAttribute(Constants.LOGIN_KEY);
			SysUser baseUser = sysUserService.getById(SysUser.class, user.getId());
			List<Map> list = new ArrayList<Map>();
			Map mo = new HashMap();
			mo.put("id", baseUser.getId());
			mo.put("userName", baseUser.getUserName());
			mo.put("realName", baseUser.getRealName());
			mo.put("createTime", baseUser.getCreatedDate()==null?"":DateUtil.getStrYMDHMByDate((baseUser).getCreatedDate()));
			mo.put("balance", baseUser.getBalance()==null?0.0:NumberUtil.formatDouble5(baseUser.getBalance()));
			mo.put("points", baseUser.getPoints()==null?0:baseUser.getPoints());
			JSONHelper.returnInfo(JSONHelper.bean2json(mo));
			return NONE;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			JSONHelper.returnInfo(JSONHelper.failedInfo(Constants.failed_code,Constants.failed_msg));
			return NONE;
		}
    }





	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/userList", method = {RequestMethod.GET, RequestMethod.POST})
	public String userList(HttpServletRequest request, HttpServletResponse response,String search) {
		try {
			if(!StringUtils.isBlank(search)){
				search = URLDecoder.decode(search,"UTF-8");
			}
			List page = sysUserService.userList(search);
			List<Map> list = new ArrayList<Map>();
			for(Object sm:page){
				Map mo = new HashMap();
				mo.put("id", ((SysUser)sm).getId());
				mo.put("userName", ((SysUser)sm).getUserName());
				mo.put("realName", ((SysUser)sm).getRealName());
				mo.put("empNumber", ((SysUser)sm).getEmpNumber());
				mo.put("department", ((SysUser)sm).getDepartment());
				mo.put("position", ((SysUser)sm).getPosition());
				mo.put("createTime", ((SysUser)sm).getCreatedDate()==null?"":DateUtil.getStrYMDHMByDate(((SysUser)sm).getCreatedDate()));
				mo.put("status", ((SysUser)sm).getStatus()==null||((SysUser)sm).getStatus().equals(Entity.Status.effective.name())?"有效":"无效");
				list.add(mo);
			}
			JSONHelper.returnInfo(JSONHelper.list2json(list,page.size()+""));
			return NONE;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			JSONHelper.returnInfo(JSONHelper.failedInfo(Constants.failed_code,Constants.failed_msg));
			return NONE;
		}
    }

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveUser", method = {RequestMethod.GET, RequestMethod.POST})
	public String saveUser(HttpServletRequest request, HttpServletResponse response,
			String userName,
			String password,
			String realName,
			String empNumber,
			String position,
			String status,
			String newRoles,
			String department,
			Boolean isMobileUser,
			Long proxyId,
			String mobile) {
		try {
			if(StringUtils.isBlank(userName)||
					StringUtils.isBlank(realName)||
					StringUtils.isBlank(position)){
				JSONHelper.returnInfo(JSONHelper.failedInfo(Constants.data_err_code,Constants.data_err_msg));
				return NONE;
			}
			if("A0001".equals(department)&&(proxyId==null)){//如果是代理
				JSONHelper.returnInfo(JSONHelper.failedInfo(Constants.data_err_code,Constants.data_err_msg));
				return NONE;
			}
			//加入代理区分
			SysUser  dd = new SysUser();
			dd.setCreatedDate(new Date());

			SysUser dd2 = sysUserService.queryUserByName(userName);
			if(dd2!=null){
				JSONHelper.returnInfo(JSONHelper.failedInfo(Constants.data_err_code,"用户名已被占用"));
				return NONE;
			}
			if(!StringUtils.isBlank(userName)){
				dd.setUserName(userName);
			}
			if(!StringUtils.isBlank(password)){
				password = MD5.getInstance().getMD5(password);
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
			if(proxyId!=null&&proxyId!=-1L){
				dd.setDepartment("A0001");
				dd.setDepartmentId(proxyId);//把代理的ID设置成部门ID
				dd.setProxyAdmin(false);
			}else{
				dd.setDepartment("B0001");
				dd.setDepartmentId(proxyId);
			}
			if(!StringUtils.isBlank(position)){
				dd.setPosition(position);
			}
			if(!StringUtils.isBlank(status)){
				dd.setStatus(status);
			}
			if(isMobileUser!=null){
				dd.setMobileUser(isMobileUser);
			}
			if(!StringUtils.isBlank(newRoles)){
				List<SysRole> roles = sysUserService.listRolesByIDString(newRoles);
				dd.getRoles().clear();
				dd.getRoles().addAll(roles);
			}else{
				dd.getRoles().clear();
			}
			sysUserService.save(dd);
			info("设置用户：用户名："+dd.getRealName());
			JSONHelper.returnInfo(JSONHelper.successInfo(Constants.success_code,Constants.success_msg));
			return NONE;
		} catch (Exception e) {
			error("设置用户失败！");
			e.printStackTrace();
			JSONHelper.returnInfo(JSONHelper.failedInfo(Constants.failed_code,Constants.failed_msg));
			return NONE;
		}
    }


	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/editUser", method = {RequestMethod.GET, RequestMethod.POST})
	public String editUser(HttpServletRequest request, HttpServletResponse response,
			String userId,
			String password,
			String realName,
			String empNumber,
			String position,
			String status,
			String newRoles,
			String department,
			Boolean isMobileUser,
			Long proxyId,
			String mobile) {
		try {
			if(StringUtils.isBlank(realName)||StringUtils.isBlank(position)||StringUtils.isBlank(userId)){
				JSONHelper.returnInfo(JSONHelper.failedInfo(Constants.data_err_code,Constants.data_err_msg));
				return NONE;
			}
			if("A0001".equals(department)&&(proxyId==null)){//如果是代理
				JSONHelper.returnInfo(JSONHelper.failedInfo(Constants.data_err_code,Constants.data_err_msg));
				return NONE;
			}
			SysUser sysUser = sysUserService.getById(SysUser.class,Long.valueOf(userId));
			if(sysUser.getDepartment().equals("C0001")){
				JSONHelper.returnInfo(JSONHelper.failedInfo(Constants.data_err_code,"合作商在商家管理里修改!"));
				return NONE;
			}
			//加入代理区分
			SysUser  dd = null;
			//如果ID是编辑
			if(!StringUtils.isBlank(userId)){
				dd = (SysUser)sysUserService.getById(SysUser.class, Long.valueOf(userId));
			}
			if(!StringUtils.isBlank(password)){
				password = MD5.getInstance().getMD5(password);
				dd.setPassword(password);
			}
			if(!StringUtils.isBlank(realName)){
				dd.setRealName(realName);
			}
			if(!StringUtils.isBlank(empNumber)){
				dd.setEmpNumber(empNumber);
			}
			if(proxyId!=null&&proxyId!=-1L){
				dd.setDepartment("A0001");
				dd.setDepartmentId(proxyId);//把代理的ID设置成部门ID
			}else{
				dd.setDepartment("B0001");
				dd.setDepartmentId(proxyId);//把代理的ID设置成部门ID
			}
			if(!StringUtils.isBlank(position)){
				dd.setPosition(position);
			}
			if(!StringUtils.isBlank(status)){
				dd.setStatus(status);
			}
			if(isMobileUser!=null){
				dd.setMobileUser(isMobileUser);
			}
			if(!StringUtils.isBlank(mobile)){
				dd.setMobile(mobile);
			}
			if(!StringUtils.isBlank(newRoles)){
				List<SysRole> roles = sysUserService.listRolesByIDString(newRoles);
				dd.getRoles().clear();
				dd.getRoles().addAll(roles);
			}else{
				dd.getRoles().clear();
			}
			sysUserService.save(dd);
			info("设置用户：用户名："+dd.getRealName());
			JSONHelper.returnInfo(JSONHelper.successInfo(Constants.success_code,Constants.success_msg));
			return NONE;
		} catch (Exception e) {
			error("设置用户失败！");
			e.printStackTrace();
			JSONHelper.returnInfo(JSONHelper.failedInfo(Constants.failed_code,Constants.failed_msg));
			return NONE;
		}
    }

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/changePassword", method = {RequestMethod.GET, RequestMethod.POST})
	public String changePassword(HttpServletRequest request,
			HttpServletResponse response,
			String srcPassword,
			String password) {
		try {
			if(StringUtils.isBlank(password)||StringUtils.isBlank(srcPassword)){
				JSONHelper.returnInfo(JSONHelper.appCode2json("密码不能为空"));
				return NONE;
			}
			//加入代理区分
			HttpSession session = request.getSession();
			SysUser user = (SysUser)session.getAttribute(Constants.LOGIN_KEY);
			SysUser dd = (SysUser)sysUserService.getById(SysUser.class, Long.valueOf(user.getId()));
			srcPassword = MD5.getInstance().getMD5(srcPassword);
			if(!srcPassword.equals(dd.getPassword())){
				JSONHelper.returnInfo(JSONHelper.appCode2json("原始密码错误"));
				return NONE;
			}
			password = MD5.getInstance().getMD5(password);
			dd.setPassword(password);
			sysUserService.save(dd);
			info("修改密码：用户名："+dd.getRealName());
			JSONHelper.returnInfo(JSONHelper.successInfo(Constants.success_code,Constants.success_msg));
			return NONE;
		} catch (Exception e) {
			error("修改密码失败！");
			e.printStackTrace();
			JSONHelper.returnInfo(JSONHelper.failedInfo(Constants.failed_code,Constants.failed_msg));
			return NONE;
		}
    }

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/queryUser", method = {RequestMethod.GET, RequestMethod.POST})
	public String queryUser(HttpServletRequest request, HttpServletResponse response,String userId) {
		try {
			if(!StringUtils.isBlank(userId)){
				Map ret = new HashMap();
				SysUser dd= sysUserService.getById(SysUser.class, Long.valueOf(userId));
				StringBuffer sysroles = new StringBuffer();
				for(SysRole sr:dd.getRoles()){
					sysroles.append(sr.getId()+",");
				}
				if(!StringUtils.isBlank(sysroles)){
					ret.put("sysroles", sysroles.substring(0, sysroles.length()-1));
				}
				ret.put("sysUser", dd);
				JSONHelper.returnInfo(JSONHelper.bean2json(ret,true));
	    		return NONE;
			}
			JSONHelper.returnInfo(JSONHelper.failedInfo(Constants.failed_code,Constants.failed_msg));
			return NONE;
		} catch (Exception e) {
			e.printStackTrace();
			JSONHelper.returnInfo(JSONHelper.failedInfo(Constants.failed_code,Constants.failed_msg));
			return NONE;
		}
	}

	*
	 *
	 * @Title:        listRoles
	 * @Description:  TODO 角色列表
	 * @param:        @param request
	 * @param:        @param response
	 * @param:        @param search
	 * @param:        @return
	 * @return:       String
	 * @throws
	 * @author        Administrator
	 * @Date          2015年8月11日 上午11:52:23

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/listRoles", method = {RequestMethod.GET, RequestMethod.POST})
	public String listRoles(HttpServletRequest request, HttpServletResponse response,String search) {
		try {
			if(!StringUtils.isBlank(search)){
				search = URLDecoder.decode(search,"UTF-8");
			}
			List page = sysUserService.listRoles(search);
			List<Map> list = new ArrayList<Map>();
			for(Object sm:page){
				Map mo = new HashMap();
				mo.put("id", ((SysRole)sm).getId());
				mo.put("roleName", ((SysRole)sm).getRoleName());
				list.add(mo);
			}
			JSONHelper.returnInfo(JSONHelper.list2json(list,page.size()+""));
			return NONE;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			JSONHelper.returnInfo(JSONHelper.failedInfo(Constants.failed_code,Constants.failed_msg));
			return NONE;
		}
    }




*/




}
