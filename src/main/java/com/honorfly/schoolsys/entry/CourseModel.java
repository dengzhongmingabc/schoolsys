package com.honorfly.schoolsys.entry;

import com.honorfly.schoolsys.utils.dao.EntityObj;
import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * SysUser entity. @author MyEclipse Persistence Tools
 */
@Entity

@Table(name = "teach_course_model")
@TypeDefs({
		@TypeDef(name = "string-array", typeClass = StringArrayType.class),
		@TypeDef(name = "int-array", typeClass = IntArrayType.class),
		@TypeDef(name = "json", typeClass = JsonStringType.class),
		@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class CourseModel extends EntityObj {

	@Column
	private int classType = 1;//1:一对一 2：班级

	@Column
	private Long classId = 0L;

	@Column
	private String className;

	@Column
	private long teacherId = 0L;

	@Column
	private String teacherName;

	@Column
	private String startTime;

	@Column
	private String endTime;

	@Column
	private String startDate;

	@Column
	private Integer repeatModel;//1,每周，2隔周

	@Type(type = "jsonb")
	@Column(columnDefinition = "jsonb")
	private List weekModel;//0:星期一  2：星期二  。。。。

	@Column
	private Integer courseModel;//1:自由排课 2：随机排课

	@Type(type = "jsonb")
	@Column(columnDefinition = "jsonb")
	private List events;

	public int getClassType() {
		return classType;
	}

	public void setClassType(int classType) {
		this.classType = classType;
	}

	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(long teacherId) {
		this.teacherId = teacherId;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public List getEvents() {
		return events;
	}

	public void setEvents(List events) {
		this.events = events;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public Integer getRepeatModel() {
		return repeatModel;
	}

	public void setRepeatModel(Integer repeatModel) {
		this.repeatModel = repeatModel;
	}

	public List getWeekModel() {
		return weekModel;
	}

	public void setWeekModel(List weekModel) {
		this.weekModel = weekModel;
	}

	public Integer getCourseModel() {
		return courseModel;
	}

	public void setCourseModel(Integer courseModel) {
		this.courseModel = courseModel;
	}
}
