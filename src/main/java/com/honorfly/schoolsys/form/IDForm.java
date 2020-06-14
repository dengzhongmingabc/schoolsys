package com.honorfly.schoolsys.form;

import io.swagger.annotations.ApiModelProperty;

/**
 * SysPermission entity. @author MyEclipse Persistence Tools
 */

public class IDForm {
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@ApiModelProperty(value="ID",required=true)
	private long id;



}
