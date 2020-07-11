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

	@Column
	private Boolean isLock;

	@ManyToMany(cascade=CascadeType.PERSIST,fetch = FetchType.EAGER)
	@JoinTable(
			name="sys_role_permission",
			joinColumns=@JoinColumn(name="role_id"),
			inverseJoinColumns=@JoinColumn(name="permission_id")
	)
	private Set<SysPermission> permissions = new HashSet<SysPermission>();


	@ManyToMany(cascade=CascadeType.PERSIST,fetch = FetchType.EAGER)
	@JoinTable(
			name="role_School",
			joinColumns=@JoinColumn(name="role_id"),
			inverseJoinColumns=@JoinColumn(name="school_id")
	)
	private Set<School> schools = new HashSet<>();
	// Constructors
	@Column
	private int selectType;

	public Boolean getLock() {
		return isLock;
	}

	public void setLock(Boolean lock) {
		isLock = lock;
	}

	public int getSelectType() {
		return selectType;
	}

	public void setSelectType(int selectType) {
		this.selectType = selectType;
	}

	public Set<School> getSchools() {
		return schools;
	}

	public void setSchools(Set<School> schools) {
		this.schools = schools;
	}

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
