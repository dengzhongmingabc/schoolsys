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

    //title
    private String title;
    //name
    private String name;
    //组件
    private String component;

    private Boolean isShow;
    //redirect
    private String redirect;

    private Long parentId;
    private Boolean isLeaf;


    private String icon;

    private SysPermission parent;

    @Column
    public Boolean getShow() {
        return isShow;
    }

    public void setShow(Boolean show) {
        isShow = show;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column
    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }



    @Column(name = "icon")
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
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
