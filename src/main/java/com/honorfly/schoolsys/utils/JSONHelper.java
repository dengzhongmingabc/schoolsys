package com.honorfly.schoolsys.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class JSONHelper {

	private static Logger logger = LoggerFactory.getLogger(JSONHelper.class);

	public static void returnInfoAccessControlAllowOrigin(String jso){
		HttpServletResponse response =  ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
		HttpServletRequest request = (HttpServletRequest) ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		response.setContentType("text/json; charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		logger.info("\n url:"+request.getRequestURI()+":\n"+JSONFormat.format(jso));
		try {
			PrintWriter out = response.getWriter();
			out.print(jso);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void returnInfo(HttpServletResponse response, String jso){
		response.setContentType("text/json; charset=utf-8");
		HttpServletRequest request = (HttpServletRequest) ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		logger.info("\n url:"+request.getRequestURI()+":\n"+JSONFormat.format(jso));
		try {
			PrintWriter out = response.getWriter();
			out.print(jso);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void returnInfo(String jso){
		HttpServletResponse response =  ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
		response.setContentType("text/json; charset=utf-8");
		HttpServletRequest request = (HttpServletRequest) ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		logger.info("\n url:"+request.getRequestURI()+":\n"+JSONFormat.format(jso));
		try {
			PrintWriter out = response.getWriter();
			out.print(jso);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void returnInfo(String jso,String ctype){
		HttpServletResponse response =  ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
		response.setContentType(""+ctype+"; charset=utf-8");
		HttpServletRequest request = (HttpServletRequest) ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		logger.info("\n url:"+request.getRequestURI()+":\n"+JSONFormat.format(jso));
		try {
			PrintWriter out = response.getWriter();
			out.print(jso);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 只有错误信息
	 * @return
	 */
	public static String returnServerErrJsonString(){
		return returnJsonString(AppConst.SERVER_ERR_CODE, AppConst.SERVER_ERR_MSG, null);
	}

	/**
	 * 自定义错误信息
	 * @param msg
	 * @return
	 */
	public static String returnServerErrJsonString(String msg){
		return returnJsonString(AppConst.ERR_CODE, msg, null);
	}

	/**
	 * 只返回正确信息
	 * @return
	 */
	public static String returnServerSuccessJsonString(){
		return returnJsonString(AppConst.SERVER_OK_CODE, AppConst.SERVER_OK_MSG, null);
	}

	/**
	 * 返回数据
	 * @param data
	 * @return
	 */
	public static String returnServerSuccessJsonString(Object data){
		return returnJsonString(AppConst.SERVER_OK_CODE, AppConst.SERVER_OK_MSG, data);
	}

	/**
	 *
	 * @param code
	 * @param msg
	 * @return
	 */
	public static String returnJsonString(String code, String msg){
		return returnJsonString(code, msg, null);
	}

	public static String returnJsonString(String code, String msg, Object data){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("code", code);
			map.put("msg", msg);
			map.put("data",data);
			ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(map);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
