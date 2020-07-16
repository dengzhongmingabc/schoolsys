package com.honorfly.schoolsys.service.impl;

import com.honorfly.schoolsys.entry.School;
import com.honorfly.schoolsys.service.ICourseService;
import com.honorfly.schoolsys.utils.dao.IBaseDao;
import com.honorfly.schoolsys.utils.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CourseServiceImpl extends BaseService implements ICourseService {

	@Autowired
	private IBaseDao baseDaoImpl;



}
