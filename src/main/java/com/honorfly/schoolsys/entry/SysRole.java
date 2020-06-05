package com.honorfly.schoolsys.entry;

import com.honorfly.schoolsys.utils.dao.EntityObj;

import javax.persistence.*;
import java.util.*;

/**
 * SysRole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_role")
public class SysRole  extends EntityObj {

	// Fields

	@Column
	private String roleName;
	@Column
	private Date createdDate;

	@ManyToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinTable(
			name="sys_role_permission",
			joinColumns=@JoinColumn(name="role_id"),
			inverseJoinColumns=@JoinColumn(name="permission_id")
	)
	private Set<SysPermission> permissions = new HashSet<SysPermission>();




	public Set<SysPermission> getPermissions() {
		return permissions;
	}


	public void setPermissions(Set<SysPermission> permissions) {
		this.permissions = permissions;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public Date getCreatedDate() {
		return createdDate;
	}

	@Override
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


}