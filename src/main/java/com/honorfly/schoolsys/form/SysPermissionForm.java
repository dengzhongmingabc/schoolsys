package com.honorfly.schoolsys.form;

import com.honorfly.schoolsys.utils.dao.EntityObj;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * SysPermission entity. @author MyEclipse Persistence Tools
 */

public class SysPermissionForm {

	@ApiModelProperty(value="按钮名称",required=true)
	@NotEmpty
	private String permissionName;
	@ApiModelProperty(value="URL",required=true)
	@NotEmpty
	private String permissionUrl;

	@ApiModelProperty(value="父ID")
	private Long parentId;

	@ApiModelProperty(value="是否是叶子",required=true)
	private Boolean isLeaf;

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getPermissionUrl() {
		return permissionUrl;
	}

	public void setPermissionUrl(String permissionUrl) {
		this.permissionUrl = permissionUrl;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}



	public Boolean getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
}