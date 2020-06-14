package com.honorfly.schoolsys.form;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;


public class SysRoleForm  {
	@ApiModelProperty(value="角色Id",required=true)
	@NotEmpty
	private String roleId;

	@ApiModelProperty(value="状态",required=true)
	@NotEmpty
	private String status;

	@ApiModelProperty(value="名称",required=true)
	@NotEmpty
	private String roleName;

	@ApiModelProperty(value="权限集",required=true)
	private String newpermissions;

	public String getNewpermissions() {
		return newpermissions;
	}

	public void setNewpermissions(String newpermissions) {
		this.newpermissions = newpermissions;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
