package com.honorfly.schoolsys.utils.service;


import com.honorfly.schoolsys.utils.dao.EntityObj;

import java.util.List;
import java.util.Map;


public interface IBaseService{


	public BaseService setClass(Class class1);

	public void delete(Long id)throws Exception;

	public void delete(EntityObj data)throws Exception ;

	public void deleteByID(Long id)throws Exception ;
	
	public <T> void delete(Class<T> clazz, Long id) throws Exception;

	public Long save(EntityObj data) throws Exception;

	public void save(Object entity);

	public void update(Object entity);
	
	public <T> T getById(Class<T> clazz, Object id);
	
	public Page getDataPageBySQL(String sql, Class clazz, Map<String, String> args, int curPage, int pageSize)
			throws Exception;

	public Page getDataPageByJPQL(String sql, Map<String, String> args, int curPage, int pageSize)
			throws Exception;
	
	public List loadMapBySQL(final String sql, final Map<String, String> args)throws Exception;

	public List loadBySQL(final String sql, final Map<String, String> args, Class clazz)throws Exception;
}
