package com.honorfly.schoolsys.utils.web;

import com.honorfly.schoolsys.entry.SessionUser;
import com.honorfly.schoolsys.utils.*;
import com.honorfly.schoolsys.utils.dao.EntityObj;
import com.honorfly.schoolsys.utils.redis.RedisUtil;
import com.honorfly.schoolsys.utils.service.BaseService;
import com.honorfly.schoolsys.utils.service.Page;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class BaseController{

	public static final String space = " ";
	public Class entityObjClazz;

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 *
	 */
	public static final String JSON = "json";

	protected Map<String, Object> dto;

	private static String basePath;

	private static final String entityPackage="com.ydy258.ydy.entity";

	protected static final int pageSize = 20;

	@Autowired
	protected RedisUtil redisUtil;
	@Autowired
	protected AppConfig appConfig;

	@Autowired
	protected BaseService baseService;

	/** servletContext */

	@Autowired
	protected ServletContext servletContext;

	@Autowired
	protected HttpServletRequest request;


	public SessionUser getRedisSession(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		SessionUser sessionUser = (SessionUser) auth.getPrincipal();
		return (SessionUser) redisUtil.get(AppConst.Redis_Session_Namespace + sessionUser.getId());
	}

	@ApiOperation(value="分页查询")
	@ResponseBody
	@RequestMapping(value = "/pageList",method = RequestMethod.POST)
	public Result pageList(String search, @RequestBody Map<String,String> args, int pageNo, int pageSize) throws Exception{
		String tableName = TableInfoUtils.getTableName(this.entityObjClazz);
		StringBuffer sql = new StringBuffer();
		sql.append("select * from");
		sql.append(space);
		sql.append(tableName);
		sql.append(space);
		sql.append("where");
		sql.append(space);
		sql.append("admin_id="+getRedisSession().getAdminId());
		sql.append(space);
		sql.append(" and invalid=true");
		if (!StringUtils.isBlank(search)){
			sql.append(space);
			sql.append("and");
			sql.append(space);
			sql.append(search);
		}
		sql.append(space);
		sql.append("order by id desc");
		Page page = baseService.getDataPageBySQL(sql.toString(),this.entityObjClazz,args,pageNo,pageSize);
		return ResultGenerator.genSuccessResult(page);
	}

	@ApiOperation(value="列表查询")
	@ResponseBody
	@RequestMapping(value = "/list",method = RequestMethod.POST)
	public Result list(String search, Map<String,String> args) throws Exception{
		String tableName = TableInfoUtils.getTableName(this.entityObjClazz);
		StringBuffer sql = new StringBuffer();
		sql.append("select * from");
		sql.append(space);
		sql.append(tableName);
		sql.append(space);
		sql.append("where");
		sql.append(space);
		sql.append("admin_id="+getRedisSession().getAdminId());
		sql.append(space);
		sql.append("and invalid=true");
		if (!StringUtils.isBlank(search)){
			sql.append(space);
			sql.append("and");
			sql.append(space);
			sql.append(search);
		}
		sql.append(space);
		sql.append("order by id desc");
		List resultList = baseService.loadBySQL(sql.toString(),args,this.entityObjClazz);
		return ResultGenerator.genSuccessResult(resultList);
	}

	@ApiOperation(value="ID查询")
	@ResponseBody
	@RequestMapping(value = "/query",method = RequestMethod.POST)
	public Result query(long id) throws Exception{
		EntityObj entityObj = (EntityObj) baseService.getById(this.entityObjClazz,id);
		if (entityObj!=null){
			return ResultGenerator.genSuccessResult(entityObj);
		}
		return ResultGenerator.genFailResult("没有对应的数据");
	}

	@ApiOperation(value="新增")
	@ResponseBody
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	public Result save( @RequestBody Map<String,String> args) throws Exception{
		EntityObj obj = (EntityObj) entityObjClazz.newInstance();
		for(Map.Entry<String, String> entry : args.entrySet()){
			obj.setPropertyValue(entry.getKey(),entry.getValue());
		}
		baseService.save(obj);
		return ResultGenerator.genSuccessResult();
	}

	@ApiOperation(value="逻辑删除")
	@ResponseBody
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	public Result delete(long id) throws Exception{
		baseService.logicDelete(entityObjClazz,id);
		return ResultGenerator.genSuccessResult();
	}


	public Long getSchoolId(){
		return this.getRedisSession().getSchoolId();
	}

	public Long getAdminId(){
		return this.getRedisSession().getAdminId();
	}

	public BaseController() {
		dto = new HashMap<String, Object>();
	}

	public Map<String, Object> getDto() {
		return dto;
	}

	public void setDto(Map<String, Object> dto) {
		this.dto = dto;
	}

	public static String getBasePath() {
		basePath = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getContextPath();
		return basePath;
	}

	public static HttpServletRequest getRequest(){
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	private static final long serialVersionUID = 1L;

	protected String jsonString;

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String js) {
		jsonString = js;
	}

	public static short getShort(String key) {
		String value = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getParameter(key);
		if (value == null || value.length() == 0)
			return 0;
		else
			return Short.parseShort(value);
	}

	public static boolean getBoolean(String key) {
		String value = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getParameter(key);
		if (value == null || value.length() == 0)
			return false;
		else
			return Boolean.valueOf(value).booleanValue();
	}

	public static double getDouble(String key) {
		String value = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getParameter(key);
		if (value == null || value.length() == 0)
			return 0.00;
		else
			return Double.valueOf(value).doubleValue();
	}

	/**
	 * 获得传�?�的参数�?
	 * @param key�?参数名称
	 * @return 参数�?
	 */
	public String getStringValue(String key) {
		try {
			return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getParameter(key);
		} catch (Exception ex) {
			return "";
		}
	}

	/**
	 * 获得传�?�的参数�?
	 * @param key�?参数名称
	 * @return 参数�?
	 */
	public String[] getStringValues(String key) {
		return ((String[]) ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getParameterValues(key));
	}


	/**
	 * 获得传�?�的参数整形�?
	 * @param key 参数名称
	 * @return 整形�?
	 */
	public int getIntValue(String key) {
		String value = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getParameter(key);
		if (value == null || value.length() == 0)
			return 0;
		else
			return Integer.parseInt(value);
	}


	/**
	 * 获得传�?�的参数长整形�??
	 * @param key 参数名称
	 * @return 长整形�??
	 */
	public long getLongValue(String key) {
		String value = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getParameter(key);
		if (value == null || value.length() == 0)
			return 0L;
		else
			return Long.parseLong(value);
	}





	public Object getModel() {
		return null;
	}

	/**
	 * 得到session�?
	 * @param key
	 * @return
	 */
	public Object getSession(String key) {
		return  ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession().getAttribute(key);
	}

	/**
	 * 设置session�?
	 * @param key
	 * @param value
	 */
	@SuppressWarnings("unchecked")
	public void setSession(String key, Object value) {
		((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession().setAttribute(key, value);
	}


	/**
	 * 判断请求的method是不是post
	 * @return 如果是post则返回true, 否则返回false
	 */
	public boolean isPost() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request.getMethod().toUpperCase().equals("POST");
	}


	public Class getEntityByName(String entityName){
		StringBuffer sb = new StringBuffer(entityPackage).append(".").append(entityName);
		try {
			Class c =Class.forName(sb.toString());
			return c;
		} catch (Exception e) {
			return null;
		}
	}

}
