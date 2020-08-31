package com.honorfly.schoolsys.entry;

import com.honorfly.schoolsys.utils.dao.EntityObj;
import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;

/**
 * SysUser entity. @author MyEclipse Persistence Tools
 */
@Entity

@Table(name = "teach_student_teacher")
@TypeDefs({
		@TypeDef(name = "string-array", typeClass = StringArrayType.class),
		@TypeDef(name = "int-array", typeClass = IntArrayType.class),
		@TypeDef(name = "json", typeClass = JsonStringType.class),
		@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class TeachStudentTeacher extends EntityObj {

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

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@NotFound(action= NotFoundAction.IGNORE)
	//@JoinColumn(name = "teacherId", insertable = false,updatable = false)
	@JoinColumn(
			name = "teacherId", insertable = false,updatable = false,
			foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
	private SysUser teacher;
	@Column
    private Long teacherId=0L;

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "schoolId", insertable = false, updatable = false)
	private School school;

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@NotFound(action= NotFoundAction.IGNORE)
	@JoinColumn(
			name = "courseModelId", insertable = false,updatable = false,
			foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
	private CourseModel courseModel;
	@Column
	private Long courseModelId=0L;

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

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public CourseModel getCourseModel() {
		return courseModel;
	}

	public void setCourseModel(CourseModel courseModel) {
		this.courseModel = courseModel;
	}

	public Long getCourseModelId() {
		return courseModelId;
	}

	public void setCourseModelId(Long courseModelId) {
		this.courseModelId = courseModelId;
	}
}
