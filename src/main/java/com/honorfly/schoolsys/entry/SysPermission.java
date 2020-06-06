package com.honorfly.schoolsys.entry;

import com.honorfly.schoolsys.utils.dao.EntityObj;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * SysPermission entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_permission")
public class SysPermission extends EntityObj {


	@Column(name = "permission_name")
	private String permissionName;
	@Column(name = "permission_url")
	private String permissionUrl;


	@Column(name = "parent_id")
	private Long parentId;

	@Column(name = "is_leaf")
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