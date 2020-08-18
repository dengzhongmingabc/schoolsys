package com.honorfly.schoolsys.entry;

import com.alibaba.fastjson.JSONArray;
import com.honorfly.schoolsys.utils.dao.EntityObj;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

/**
 * SysUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "market_student")
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class MarketStudent extends EntityObj {

	@Column
	private String studentName;

	@Column
	private String mobile;

	@Column
	private String seekPerson;//1,母亲，2,父亲，3,自己

	@Column
	private int studentSex;//1男，2女 ，3未知

	@Column
	private String introduce;//介绍人

	@Column
	private int seekModel;//1来电。2来访，3网络，其它

	@Column
	private int seekDepth;//1,2,3,4,5 意向度

	@Type(type = "json")
	@Column(columnDefinition = "json")
	private JSONArray seekCourse;//咨询课程

	@Type(type = "json")
	@Column(columnDefinition = "json")
	private List<Map> tags;//标签

	@Type(type = "json")
	@Column(columnDefinition = "json")
	private List<Map> talkMark;//沟通记录

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "seekSchoolId", insertable = false, updatable = false)
	private School seekSchool;

	@Column
	private Long seekSchoolId;//咨询校区

	@Column
	private String remark;//备注

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "marketPersonFirstId", insertable = false, updatable = false)
	private SysUser marketPersonFirst;

	@Column
	private Long marketPersonFirstId;//主负责人ID

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "marketPersonSecondId", insertable = false, updatable = false)
	private SysUser marketPersonSecond;

	@Column
	private Long marketPersonSecondId;//次负责人ID
	@Column
	private Boolean forbidden=false;

	@Column
	private Boolean inward = false;

	public Boolean getInward() {
		return inward;
	}

	public void setInward(Boolean inward) {
		this.inward = inward;
	}

	public Long getMarketPersonFirstId() {
		return marketPersonFirstId;
	}

	public void setMarketPersonFirstId(Long marketPersonFirstId) {
		this.marketPersonFirstId = marketPersonFirstId;
	}

	public Long getMarketPersonSecondId() {
		return marketPersonSecondId;
	}

	public void setMarketPersonSecondId(Long marketPersonSecondId) {
		this.marketPersonSecondId = marketPersonSecondId;
	}

	public Boolean getForbidden() {
		return forbidden;
	}

	public void setForbidden(Boolean forbidden) {
		this.forbidden = forbidden;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSeekPerson() {
		return seekPerson;
	}

	public void setSeekPerson(String seekPerson) {
		this.seekPerson = seekPerson;
	}

	public int getStudentSex() {
		return studentSex;
	}

	public void setStudentSex(int studentSex) {
		this.studentSex = studentSex;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public int getSeekModel() {
		return seekModel;
	}

	public void setSeekModel(int seekModel) {
		this.seekModel = seekModel;
	}

	public int getSeekDepth() {
		return seekDepth;
	}

	public void setSeekDepth(int seekDepth) {
		this.seekDepth = seekDepth;
	}

	public School getSeekSchool() {
		return seekSchool;
	}

	public void setSeekSchool(School seekSchool) {
		this.seekSchool = seekSchool;
	}

	public Long getSeekSchoolId() {
		return seekSchoolId;
	}

	public void setSeekSchoolId(Long seekSchoolId) {
		this.seekSchoolId = seekSchoolId;
	}

	public SysUser getMarketPersonFirst() {
		return marketPersonFirst;
	}

	public void setMarketPersonFirst(SysUser marketPersonFirst) {
		this.marketPersonFirst = marketPersonFirst;
	}

	public SysUser getMarketPersonSecond() {
		return marketPersonSecond;
	}

	public void setMarketPersonSecond(SysUser marketPersonSecond) {
		this.marketPersonSecond = marketPersonSecond;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public JSONArray getSeekCourse() {
		return seekCourse;
	}

	public void setSeekCourse(JSONArray seekCourse) {
		this.seekCourse = seekCourse;
	}

	public List<Map> getTags() {
		return tags;
	}

	public void setTags(List<Map> tags) {
		this.tags = tags;
	}

	public List<Map> getTalkMark() {
		return talkMark;
	}

	public void setTalkMark(List<Map> talkMark) {
		this.talkMark = talkMark;
	}
}
