package com.honorfly.schoolsys.form;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;

/**
 * SysPermission entity. @author MyEclipse Persistence Tools
 */

public class UserLoginForm {

	@ApiModelProperty(value="用戶名稱",required=true)
	@NotEmpty
	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@ApiModelProperty(value="password",required=true)
	@NotEmpty
	private String password;


}
