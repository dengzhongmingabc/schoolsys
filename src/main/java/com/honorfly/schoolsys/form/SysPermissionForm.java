package com.honorfly.schoolsys.form;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;

/**
 * SysPermission entity. @author MyEclipse Persistence Tools
 */

public class SysPermissionForm {

	@ApiModelProperty(value="ID")
	private Long id;

	@ApiModelProperty(value="标题",required=true)
	@NotEmpty
	private String title;
	@ApiModelProperty(value="URL")
	@NotEmpty
	private String redirect;

	@ApiModelProperty(value="父ID")
	private Long parentId=0L;

	@ApiModelProperty(value="是否是叶子")
	private Boolean isLeaf=false;

	@ApiModelProperty(value="icon")
	private String icon;

	@ApiModelProperty(value="名称",required=true)
	private String name;
	@ApiModelProperty(value="组件",required=true)
	private String component;
	@ApiModelProperty(value="是否显示")
	private Boolean isShow = true;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public Boolean getShow() {
		return isShow;
	}

	public void setShow(Boolean show) {
		isShow = show;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
