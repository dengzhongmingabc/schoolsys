package com.honorfly.schoolsys.utils;


//
public final class AppConst {

	public static final String USER_KEY = "USER_KEY";

	public static final String SERVER_OK_CODE = "0200";
	public static final String SERVER_OK_MSG = "操作成功";

	public static final String SERVER_ERR_CODE = "0500";
	public static final String SERVER_ERR_MSG = "服务器错误";

	public static final String ERR_CODE = "0400";

	public static final String UNAUTH_ERR_CODE = "0401";
	public static final String UNAUTH_ERR_MSG = "账号或者密码错误";

	public static final String FORBIDDEN_ERR_CODE = "0403";
	public static final String FORBIDDEN_ERR_MSG = "没有权限";

	public static final String ARGS_ERR_CODE = "0402";
	public static final String ARGS_ERR_MSG = "必要参数为空";

	public static final String NOFIND_ERR_CODE = "0404";
	public static final String NOFIND_ERR_MSG = "没有找到对应的数据";


	public static final String Redis_Session_Namespace = "SessionNamespace:";
	public static final String Redis_Captcha_Namespace = "CaptchaNamespace:";
}
