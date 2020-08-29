package com.honorfly.schoolsys.service;

import com.honorfly.schoolsys.entry.TeachStudentCourse;
import com.honorfly.schoolsys.entry.TeachStudentTeacher;
import com.honorfly.schoolsys.utils.service.IBaseService;

public interface ITeachService extends IBaseService {
	public TeachStudentCourse queryCount(long studentId, long courseId) throws Exception;

	public TeachStudentTeacher queryTeachStudentTeacher(long studentId, long courseId) throws Exception;
}
