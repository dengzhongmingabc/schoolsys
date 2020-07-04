package com.honorfly.schoolsys.service.impl;

import com.honorfly.schoolsys.entry.School;
import com.honorfly.schoolsys.service.ISchoolManagerService;
import com.honorfly.schoolsys.utils.BaseException;
import com.honorfly.schoolsys.utils.dao.IBaseDao;
import com.honorfly.schoolsys.utils.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SchoolServiceImpl extends BaseService implements ISchoolManagerService {

	@Autowired
	private IBaseDao baseDaoImpl;

	//查询列表
	public List schoolList(long adminId) throws Exception {
		List list = baseDaoImpl.loadBySQL(" select * from school where admin_id="+adminId+" and invalid=true", null, School.class);
		return list;
	}

	//逻辑删除
	public void delete(Long adminId,long schoolId) throws Exception {
		School school = this.getById(School.class,schoolId);
		if(school==null||!adminId.equals(school.getAdminId())){
			throw new BaseException("没有查相关数据！");
		}
		school.setInvalid(false);//逻辑删除
		baseDaoImpl.save(school);
	}


	//通过ID集查询school
	public List schoolList(long adminId,String schoolIdStr) throws Exception {
		List list = baseDaoImpl.loadBySQL(" select * from school where admin_id="+adminId+" and invalid=true and id in("+schoolIdStr+")", null, School.class);
		return list;
	}


}
