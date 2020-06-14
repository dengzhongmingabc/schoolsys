package com.honorfly.schoolsys.form;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;

/**
 * SysPermission entity. @author MyEclipse Persistence Tools
 */

public class SysPermissionForm {

	@ApiModelProperty(value="ID")
	private Long key;

	@ApiModelProperty(value="按钮名称",required=true)
	@NotEmpty
	private String permissionName;
	@ApiModelProperty(value="URL",required=true)
	@NotEmpty
	private String permissionUrl;

	@ApiModelProperty(value="父ID")
	private Long parentId=-1L;

	@ApiModelProperty(value="是否是叶子",required=true)
	private Boolean isLeaf=false;

	@ApiModelProperty(value="icon")
	private String icon;

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public Boolean getLeaf() {
		return isLeaf;
	}

	public void setLeaf(Boolean leaf) {
		isLeaf = leaf;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}



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
