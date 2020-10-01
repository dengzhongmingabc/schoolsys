package com.honorfly.schoolsys.utils.securiry;


import java.util.ArrayList;
import java.util.List;

//
public final class AppWhiteList {

	//所以登录过的用户，不用授权都有资源
	public static  List<String> needLoginWhiteList = new ArrayList<String>();


	//不用登录就可访问的资源
	public static  List<String> notNeedLoginWhiteList = new ArrayList<String>();

	static {
		//登录
		needLoginWhiteList.add("/sys/user/info");
		needLoginWhiteList.add("/sys/user/nav");
		//学校创建
		needLoginWhiteList.add("/school/schoolList");
		needLoginWhiteList.add("/school/schoolAdd");
		needLoginWhiteList.add("/school/schoolIDSetting");
		needLoginWhiteList.add("/school/schoolQuery");
		//角色
		needLoginWhiteList.add("/sys/user/roleList");
		needLoginWhiteList.add("/sys/user/detailSysUser");
		needLoginWhiteList.add("/sys/user/queryRoleDetail");
		//用户
		needLoginWhiteList.add("/sys/user/roleListPermission");
	}


	static {
		notNeedLoginWhiteList.add("/login");
		notNeedLoginWhiteList.add("/sys/user/getCaptcha");
		notNeedLoginWhiteList.add("/sys/user/logout");

		//微信访问接口
		notNeedLoginWhiteList.add("/wechatAction/getCodeUrl");
		notNeedLoginWhiteList.add("/wechatAction/getSNSUserInfo");
	}
}
