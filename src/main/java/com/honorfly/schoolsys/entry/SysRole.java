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

	@Column(name = "role_name")
	private String roleName;

	@ManyToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinTable(
			name="sys_role_permission",
			joinColumns=@JoinColumn(name="role_id"),
			inverseJoinColumns=@JoinColumn(name="permission_id")
	)
	private Set<SysPermission> permissions = new HashSet<SysPermission>();

	//public List<SysPermission> buttons = new ArrayList<SysPermission>();
	// Constructors




	public Set<SysPermission> getPermissions() {
		return permissions;
	}


	public void setPermissions(Set<SysPermission> permissions) {
		this.permissions = permissions;
	}





	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}




}
