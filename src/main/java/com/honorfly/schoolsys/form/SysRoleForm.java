package com.honorfly.schoolsys.form;

import io.swagger.annotations.ApiModelProperty;


public class SysRoleForm  {
	@ApiModelProperty(value="角色Id")
	private Long id;

	@ApiModelProperty(value="状态")
	private int status=1;

	@ApiModelProperty(value="名称")
	private String roleName;

	@ApiModelProperty(value="权限集")
	private String newpermissions;

	@ApiModelProperty(value="状态")
	private Boolean invalid;

	public Boolean getInvalid() {
		return invalid;
	}

	public void setInvalid(Boolean invalid) {
		this.invalid = invalid;
	}

	public String getNewpermissions() {
		return newpermissions;
	}

	public void setNewpermissions(String newpermissions) {
		this.newpermissions = newpermissions;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
