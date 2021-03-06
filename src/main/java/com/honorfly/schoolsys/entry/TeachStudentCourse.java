package com.honorfly.schoolsys.entry;

import com.honorfly.schoolsys.utils.dao.EntityObj;
import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;

/**
 * SysUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "teach_student_course")
@TypeDefs({
		@TypeDef(name = "string-array", typeClass = StringArrayType.class),
		@TypeDef(name = "int-array", typeClass = IntArrayType.class),
		@TypeDef(name = "json", typeClass = JsonStringType.class),
		@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class TeachStudentCourse extends EntityObj {

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "studentId", insertable = false, updatable = false)
	private MarketStudent marketStudent;
	@Column
    private Long studentId=0L;

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "courseId", insertable = false, updatable = false)
	private TeachCourse course;

	@Column
    private Long courseId=0L;
	@Column
    private int courseCount=0;
	@Column
	private int teachType = 1;//1:一对一，2：班级

	@Column
    private Long classId=0L;

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

	public int getTeachType() {
		return teachType;
	}

	public void setTeachType(int teachType) {
		this.teachType = teachType;
	}


	public MarketStudent getMarketStudent() {
		return marketStudent;
	}

	public void setMarketStudent(MarketStudent marketStudent) {
		this.marketStudent = marketStudent;
	}

	public TeachCourse getCourse() {
		return course;
	}

	public void setCourse(TeachCourse course) {
		this.course = course;
	}

	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}
}
