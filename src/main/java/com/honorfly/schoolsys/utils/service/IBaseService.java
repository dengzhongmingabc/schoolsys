package com.honorfly.schoolsys.utils.service;


import com.honorfly.schoolsys.utils.dao.EntityObj;

import java.util.List;
import java.util.Map;


public interface IBaseService{


	public Long save(EntityObj data) throws Exception;


	public <T> void delete(Class<T> clazz, Long id) throws Exception;

	public <T> void logicDelete(Class<T> clazz, Long id) throws Exception;

	public <T> T getById(Class<T> clazz, Object id) throws Exception ;

	public Page getDataPageBySQL(String sql, Class clazz, Map<String, String> args, int curPage, int pageSize)
			throws Exception;

	public Page getMapDataPageBySQL(String sql,Map<String, String> args, int curPage, int pageSize)throws Exception;

	public List loadMapBySQL(final String sql, final Map<String, String> args)throws Exception;

	public List loadBySQL(final String sql, final Map<String, String> args, Class clazz)throws Exception;
}
