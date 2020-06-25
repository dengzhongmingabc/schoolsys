package com.honorfly.schoolsys.utils.web;

import com.honorfly.schoolsys.utils.AppConfig;
import com.honorfly.schoolsys.utils.redis.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class BaseController{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 *
	 */
	public static final String JSON = "json";

	protected Map<String, Object> dto;

	private static String basePath;

	private static final String entityPackage="com.ydy258.ydy.entity";

	protected static final String session_key="session_key";

	protected static final int pageSize = 20;

	@Autowired
	protected RedisUtil redisUtil;
	@Autowired
	protected AppConfig appConfig;

	/** servletContext */

	@Autowired
	protected ServletContext servletContext;

	@Autowired
	protected HttpServletRequest request;

	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


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
