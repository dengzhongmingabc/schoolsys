package com.honorfly.schoolsys.entry;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.honorfly.schoolsys.utils.dao.EntityObj;
import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.Date;

/**
 *
 */
@Entity
@Table(name = "teach_class")
@TypeDefs({
		@TypeDef(name = "string-array", typeClass = StringArrayType.class),
		@TypeDef(name = "int-array", typeClass = IntArrayType.class),
		@TypeDef(name = "json", typeClass = JsonStringType.class),
		@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class TeachClasses extends EntityObj {
	@Column
	private String name;

	@Column
	private int personCount;

	@Column
	private int personCountCurrent=0;

	@Column
	private int classPersonCount;

	@Column
	private int studentDeleteCourses;

	@Column
	private int teacherAddCourses;

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "teacherId", insertable = false, updatable = false)
	private SysUser teacher;

	@Column
	private Long teacherId;

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "teachCourseId", insertable = false, updatable = false)
	private TeachCourse teachCourse;

	@Column
	private Long teachCourseId;


	@Column
	@JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date startDate;

	@Column
	@JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date endDate;

	@Type(type = "jsonb")
	@Column(columnDefinition = "jsonb")
	private JSONObject courseTime;

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "schoolId", insertable = false, updatable = false)
	private School school;

	@Column
	private String remark;

	public int getPersonCountCurrent() {
		return personCountCurrent;
	}

	public void setPersonCountCurrent(int personCountCurrent) {
		this.personCountCurrent = personCountCurrent;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPersonCount() {
		return personCount;
	}

	public void setPersonCount(int personCount) {
		this.personCount = personCount;
	}

	public int getClassPersonCount() {
		return classPersonCount;
	}

	public void setClassPersonCount(int classPersonCount) {
		this.classPersonCount = classPersonCount;
	}

	public int getStudentDeleteCourses() {
		return studentDeleteCourses;
	}

	public void setStudentDeleteCourses(int studentDeleteCourses) {
		this.studentDeleteCourses = studentDeleteCourses;
	}

	public int getTeacherAddCourses() {
		return teacherAddCourses;
	}

	public void setTeacherAddCourses(int teacherAddCourses) {
		this.teacherAddCourses = teacherAddCourses;
	}

	public SysUser getTeacher() {
		return teacher;
	}

	public void setTeacher(SysUser teacher) {
		this.teacher = teacher;
	}

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	public TeachCourse getTeachCourse() {
		return teachCourse;
	}

	public void setTeachCourse(TeachCourse teachCourse) {
		this.teachCourse = teachCourse;
	}

	public Long getTeachCourseId() {
		return teachCourseId;
	}

	public void setTeachCourseId(Long teachCourseId) {
		this.teachCourseId = teachCourseId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public JSONObject getCourseTime() {
		return courseTime;
	}

	public void setCourseTime(JSONObject courseTime) {
		this.courseTime = courseTime;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}
}
