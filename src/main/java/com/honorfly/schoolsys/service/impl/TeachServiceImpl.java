package com.honorfly.schoolsys.service.impl;

import com.honorfly.schoolsys.entry.TeachStudentCourse;
import com.honorfly.schoolsys.service.ITeachService;
import com.honorfly.schoolsys.utils.dao.IBaseDao;
import com.honorfly.schoolsys.utils.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TeachServiceImpl extends BaseService implements ITeachService {

	@Autowired
	private IBaseDao baseDaoImpl;

	public TeachStudentCourse queryCount(long studentId,long courseId) throws Exception {
		List<TeachStudentCourse> list = baseDaoImpl.loadBySQL("select * from teach_student_course where student_id="+studentId+" and course_id="+courseId, null, TeachStudentCourse.class);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

}
