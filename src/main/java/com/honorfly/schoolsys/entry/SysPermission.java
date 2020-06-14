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

    // Fields

    private String permissionName;
    private String permissionUrl;


    private Long parentId;
    private Boolean isLeaf;

    @Column(name = "icon")
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    private String icon;

    private SysPermission parent;
    // Constructors

    public SysPermission getParent() {
        return parent;
    }

    public void setParent(SysPermission parent) {
        this.parent = parent;
    }

    /**
     * default constructor
     */
    public SysPermission() {
    }

    @Column(name = "permission_name")
    public String getPermissionName() {
        return this.permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    @Column(name = "permission_url")
    public String getPermissionUrl() {
        return this.permissionUrl;
    }

    public void setPermissionUrl(String permissionUrl) {
        this.permissionUrl = permissionUrl;
    }

    @Column(name = "parent_id")
    public Long getParentId() {
        return this.parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }



    @Column(name = "is_leaf")
    public Boolean getIsLeaf() {
        return this.isLeaf;
    }

    public void setIsLeaf(Boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

}
