package com.honorfly.schoolsys.entry;

import com.honorfly.schoolsys.utils.dao.EntityObj;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * SysUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "teach_student_course")
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class TeachStudentCourse extends EntityObj {

	@Column
    private Long studentId=0L;
	@Column
    private Long courseId=0L;
	@Column
    private int courseCount=0;
	@Column
    private Long classId;

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public int getCourseCount() {
		return courseCount;
	}

	public void setCourseCount(int courseCount) {
		this.courseCount = courseCount;
	}

	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}
}
