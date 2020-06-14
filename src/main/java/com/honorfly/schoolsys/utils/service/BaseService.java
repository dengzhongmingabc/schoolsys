package com.honorfly.schoolsys.utils.service;

import com.honorfly.schoolsys.utils.dao.EntityObj;
import com.honorfly.schoolsys.utils.dao.IBaseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BaseService implements IBaseService {

	@Autowired
	protected IBaseDao baseDaoImpl;

	protected Logger logger = LoggerFactory.getLogger(this.getClass());


	public BaseService setClass(Class class1){
		baseDaoImpl.setClass(class1);
		return this;
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void delete(Long id)throws Exception {
		baseDaoImpl.delete(id);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void delete(EntityObj data)throws Exception {
		baseDaoImpl.delete(data);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteByID(Long id)throws Exception {
		baseDaoImpl.delete(id);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Long save(EntityObj data) throws Exception {
		if (data.id == null || data.id.toString().length() < 1)
			data.lastModifiedDate = new Date();
		baseDaoImpl.save(data);
		return data.id;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void save(Object entity) {
		// TODO Auto-generated method stub
		baseDaoImpl.save(entity);
	}

	/**
	 * 修改
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void update(Object entity) {
		// TODO Auto-generated method stub
		baseDaoImpl.update(entity);
	}

	/**
	 * 删除
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public <T> void delete(Class<T> clazz, Long id) throws Exception {
		baseDaoImpl.delete(clazz, id);

	}

	/**
	 * 查询实体
	 */
	@Override
	@Transactional(readOnly = true)
	public <T> T getById(Class<T> clazz, Object id) {
		return baseDaoImpl.getById(clazz, id);
	}

	public Page getDataPageBySQL(String sql, Class clazz, Map<String, String> args, int curPage, int pageSize)
			throws Exception// 返回视图
	{
		return PageFactory.createPageBySql(baseDaoImpl, sql,  args, clazz, curPage, pageSize);
	}

	public Page getDataPageByJPQL(String sql, Map<String, String> args, int curPage, int pageSize)
			throws Exception// 返回视图
	{
		return PageFactory.createPageByJPQL(baseDaoImpl, sql,  args, curPage, pageSize);
	}

	public List loadMapBySQL(final String sql,final Map<String,String> args)throws Exception {
		return baseDaoImpl.loadMapBySQL(sql, args);
	}

	public List loadBySQL(final String sql, final Map<String,String> args,Class clazz)throws Exception {
		return baseDaoImpl.loadBySQL(sql, args, clazz);
	}
}
