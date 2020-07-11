package com.honorfly.schoolsys.utils.dao;

import com.honorfly.schoolsys.entry.SessionUser;
import com.honorfly.schoolsys.utils.AppConfig;
import com.honorfly.schoolsys.utils.AppConst;
import com.honorfly.schoolsys.utils.redis.RedisUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.query.internal.NativeQueryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Repository(value="baseDaoImpl")
public class BaseDaoImpl implements IBaseDao {
	protected static final Log log = LogFactory.getLog(BaseDaoImpl.class);

	@Autowired
	protected RedisUtil redisUtil;
	@Autowired
	protected AppConfig appConfig;

	//注入实体管理器
	@PersistenceContext
	protected EntityManager entityManager;

	private Class<EntityObj> clazz;

	public BaseDaoImpl() {
	}

	public SessionUser getRedisSession(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		SessionUser sessionUser = (SessionUser) auth.getPrincipal();
		return (SessionUser) redisUtil.get(AppConst.Redis_Session_Namespace + sessionUser.getId());
	}

	public Long getSchoolId(){
		return this.getRedisSession().getSchoolId();
	}

	public Long getAdminId(){
		return this.getRedisSession().getAdminId();
	}

	public BaseDaoImpl(Class<EntityObj> clazz) {
		this.clazz = clazz;
	}

	@Override
	public void setClass(Class clazz) {
		this.clazz = clazz;

	}

	public Class<EntityObj> getEntityClass()throws Exception {
		return this.clazz;
	}

	@Override
	public void save(Object EntityObj) {
		// TODO Auto-generated method stub
		entityManager.persist(EntityObj);
	}

	@Override
	public EntityObj getById(Object id) {
		// TODO Auto-generated method stub
		return entityManager.find(this.clazz, id);
	}


	@Override
	@Transactional()
	public <T> T getById(Class<T> clazz, Object id) {
		// TODO Auto-generated method stub
		return entityManager.find(clazz, id);
	}

	@Override
	public void update(Object entity) {
		// TODO Auto-generated method stub
		entityManager.merge(entity);
		//entityManager.flush();
	}

	/**
	 *
	 * @Title:        save
	 * @Description:  TODO 保存
	 * @param:        @param entity
	 * @param:        @return
	 * @param:        @throws Exception
	 * @return:       Long
	 * @throws
	 * @author        Administrator
	 * @Date          2015年8月4日 下午2:57:34
	 */
	public Long save(EntityObj entity)throws Exception {
		if (entity == null)
			return null;
		if ("".equals(entity.id))
			entity.id = null;
		entityManager.persist(entity);
		return entity.id;
	}

	/**
	 *
	 * @Title:        save
	 * @Description:  TODO 保存集合
	 * @param:        @param list
	 * @param:        @throws Exception
	 * @return:       void
	 * @throws
	 * @author        Administrator
	 * @Date          2015年8月4日 下午2:58:01
	 */
	public void save(List<EntityObj> list)throws Exception {
		EntityObj entity;
		for (Iterator iterator = list.iterator(); iterator.hasNext(); save(entity))
			entity = (EntityObj) iterator.next();

	}

	/**
	 *
	 * @Title:        delete 通过ID删除
	 * @Description:  TODO
	 * @param:        @param clazz
	 * @param:        @param id
	 * @param:        @throws Exception
	 * @return:       void
	 * @throws
	 * @author        Administrator
	 * @Date          2015年8月4日 下午2:58:24
	 */
	public  void delete(Long id)throws Exception {
		EntityObj entity = this.getById(id);
		if (entity != null)
			entityManager.remove(entity);
	}

	/**
	 *
	 * @Title:        delete 删除指定实体
	 * @Description:  TODO
	 * @param:        @param entity
	 * @param:        @throws Exception
	 * @return:       void
	 * @throws
	 * @author        Administrator
	 * @Date          2015年8月4日 下午2:59:04
	 */
	public void delete(EntityObj entity)throws Exception {
		if (entity == null){
			return;
		} else {
			entityManager.remove(entity);
			return;
		}
	}

	/**
	 *
	 * @Title:        delete 通过ID删除
	 * @Description:  TODO
	 * @param:        @param clazz
	 * @param:        @param id
	 * @param:        @throws Exception
	 * @return:       void
	 * @throws
	 * @author        Administrator
	 * @Date          2015年8月4日 下午2:58:24
	 */
	public <T> void delete(Class<T> clazz,Long id)throws Exception {
		T entity = this.getById(clazz, id);
		if (entity != null)
			entityManager.remove(entity);
	}
	/**
	 *
	 * @Title:        queryNum 查询数字
	 * @Description:  TODO
	 * @param:        @param jpql
	 * @param:        @param args
	 * @param:        @return
	 * @param:        @throws Exception
	 * @return:       Object
	 * @throws
	 * @author        Administrator
	 * @Date          2015年8月4日 下午2:59:43
	 */
	public Object queryNum(final String jpql, final Map<String,String> args)throws Exception {
		if (log.isDebugEnabled()) {
			log.debug((new StringBuilder("queryNum :")).append(jpql).toString());
		}
		javax.persistence.Query query = entityManager.createQuery(jpql);
		if (args != null) {
			String s;
			for (Iterator iterator = args.keySet().iterator(); iterator.hasNext(); query.setParameter(s, args.get(s))) {
				s = (String) iterator.next();
			}
		}
		return query.getSingleResult();
	}

	/**
	 *
	 * @Title:        getSQLTotalCnt
	 * @Description:  TODO 查询总数
	 * @param:        @param sql
	 * @param:        @param args
	 * @param:        @return
	 * @param:        @throws Exception
	 * @return:       int
	 * @throws
	 * @author        Administrator
	 * @Date          2015年8月4日 下午3:00:46
	 */
	public int getSQLTotalCnt(final String sql, final Map<String,String> args)throws Exception {
		if (log.isDebugEnabled()) {
			log.debug((new StringBuilder("queryNum :")).append(sql).toString());
		}
		javax.persistence.Query query = entityManager.createNativeQuery(sql);
		if (args != null) {
			String s;
			for (Iterator iterator = args.keySet().iterator(); iterator.hasNext(); query.setParameter(s, args.get(s))) {
				s = (String) iterator.next();
			}
		}
		return Integer.parseInt(query.getSingleResult().toString());
	}

	public int getSQLTotalCnt(final String sql)throws Exception {
		return getSQLTotalCnt(sql, null);
	}

	/**
	 * jpql分布查询
	 */
	public int getJPQLTotalCnt(final String jpql,final Map<String,String> args)throws Exception {
		return Integer.parseInt(queryNum(jpql, args).toString());
	}


	/**
	 *
	 * @Title:        loadByJPQL
	 * @Description:  TODO
	 * @param:        @param jpql
	 * @param:        @param args
	 * @param:        @return
	 * @param:        @throws Exception
	 * @return:       List
	 * @throws
	 * @author        Administrator
	 * @Date          2015年8月4日 下午3:02:47
	 */
	public List loadByJPQL(final String jpql, final Map<String,String> args)throws Exception {
		if (log.isDebugEnabled()) {
			log.debug((new StringBuilder("queryNum :")).append(jpql).toString());
		}
		javax.persistence.Query query = entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT);
		if (args != null) {
			String s;
			for (Iterator iterator = args.keySet().iterator(); iterator.hasNext(); query.setParameter(s, args.get(s))) {
				s = (String) iterator.next();
			}
		}
		return query.getResultList();
	}

	/**
	 * 执行sql查询语句
	 */
	public List<EntityObj> loadByJPQL(final String jpql, final Map<String,String> args, final int start, final int size)throws Exception {
		if (log.isDebugEnabled()) {
			log.debug((new StringBuilder("queryNum :")).append(jpql).toString());
		}
		javax.persistence.Query query = entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT);
		query.setMaxResults(size);
		query.setFirstResult(start);
		if (args != null) {
			String s;
			for (Iterator iterator = args.keySet().iterator(); iterator.hasNext(); query.setParameter(s, args.get(s))) {
				s = (String) iterator.next();
			}
		}
		return query.getResultList();
	}

	/**
	 *
	 */
	public Object execute(final String jpql, final Map<String,String> args)throws Exception {
		if (log.isDebugEnabled()) {
			log.debug((new StringBuilder("queryNum :")).append(jpql).toString());
		}
		javax.persistence.Query query = entityManager.createQuery(jpql,Long.class).setFlushMode(FlushModeType.COMMIT);
		if (args != null) {
			String s;
			for (Iterator iterator = args.keySet().iterator(); iterator.hasNext(); query.setParameter(s, args.get(s))) {
				s = (String) iterator.next();
			}
		}
		return Integer.valueOf(query.executeUpdate());

	}

	/**
	 * 原生sql查询
	 */
	public List loadBySQL(final String sql, final Map<String,String> args,Class clazz)throws Exception {
		if (log.isDebugEnabled()) {
			log.debug((new StringBuilder("queryNum :")).append(sql).toString());
		}
		javax.persistence.Query query = entityManager.createNativeQuery(sql,clazz).setFlushMode(FlushModeType.COMMIT);
		if (args != null) {
			String s;
			for (Iterator iterator = args.keySet().iterator(); iterator.hasNext(); query.setParameter(s, args.get(s))) {
				s = (String) iterator.next();
			}
		}
		return query.getResultList();
	}

	/**
	 * 原生sql分页查询
	 */
	public List<EntityObj> loadBySQL(final String sql, final Map<String,String> args,Class clazz, final int start, final int size)throws Exception{
		if (log.isDebugEnabled()) {
			log.debug((new StringBuilder("queryNum :")).append(sql).toString());
		}
		javax.persistence.Query query = entityManager.createNativeQuery(sql,clazz).setFlushMode(FlushModeType.COMMIT);
		query.setMaxResults(size);
		query.setFirstResult(start);
		if (args != null) {
			String s;
			for (Iterator iterator = args.keySet().iterator(); iterator.hasNext(); query.setParameter(s, args.get(s))) {
				s = (String) iterator.next();
			}
		}
		return query.getResultList();
	}


	/**
	 * 原生sql查询
	 */
	public List loadBySQL(final String sql,Class clazz)throws Exception {
		if (log.isDebugEnabled()) {
			log.debug((new StringBuilder("queryNum :")).append(sql).toString());
		}
		javax.persistence.Query query = entityManager.createNativeQuery(sql,clazz).setFlushMode(FlushModeType.COMMIT);
		return query.getResultList();
	}

	/**
	 * 原生sql分页查询
	 */
	public List<EntityObj> loadBySQL(final String sql,Class clazz, final int start, final int size)throws Exception{
		if (log.isDebugEnabled()) {
			log.debug((new StringBuilder("queryNum :")).append(sql).toString());
		}
		javax.persistence.Query query = entityManager.createNativeQuery(sql,clazz).setFlushMode(FlushModeType.COMMIT);
		query.setMaxResults(size);
		query.setFirstResult(start);
		return query.getResultList();
	}

	/**
	 * 原生sql执行
	 */
	public Object executeSQL(final String sql, final Map<String,String> args)throws Exception {
		if (log.isDebugEnabled()) {
			log.debug((new StringBuilder("queryNum :")).append(sql).toString());
		}
		javax.persistence.Query query = entityManager.createNativeQuery(sql,Long.class).setFlushMode(FlushModeType.COMMIT);

		if (args != null) {
			String s;
			for (Iterator iterator = args.keySet().iterator(); iterator.hasNext(); query.setParameter(s, args.get(s))) {
				s = (String) iterator.next();
			}
		}
		return Integer.valueOf(query.executeUpdate());
	}

	/**
	 * 原生sql 一般关联，比较复杂的查询的时候要用到的 返回的map的List
	 */
	public List loadMapBySQL(final String sql)throws Exception {
		if (log.isDebugEnabled()) {
			log.debug((new StringBuilder("queryNum :")).append(sql).toString());
		}
		javax.persistence.Query query = entityManager
				.createNativeQuery(sql)
				.setFlushMode(FlushModeType.COMMIT)
				.unwrap(NativeQueryImpl.class)
				.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		return query.getResultList();
	}

	/**
	 * 原生sql 一般关联，比较复杂的查询的时候要用到的 返回的map的List
	 */
	public List loadMapBySQL(final String sql,final Map<String,String> args)throws Exception {
		if (log.isDebugEnabled()) {
			log.debug((new StringBuilder("queryNum :")).append(sql).toString());
		}
		javax.persistence.Query query = entityManager
				.createNativeQuery(sql)
				.setFlushMode(FlushModeType.COMMIT)
				.unwrap(NativeQueryImpl.class)
		        .setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		if (args != null) {
			String s;
			for (Iterator iterator = args.keySet().iterator(); iterator.hasNext(); query.setParameter(s, args.get(s))) {
				s = (String) iterator.next();
			}
		}
		return query.getResultList();
	}

	/**
	 *  原生sql分页查询 一般关联，比较复杂的查询的时候要用到的 返回的map  的List
	 */
	public List loadMapBySQL(final String sql,final Map<String,String> args,final int start,final int size)throws Exception {
		if (log.isDebugEnabled()) {
			log.debug((new StringBuilder("queryNum :")).append(sql).toString());
		}
		javax.persistence.Query query = entityManager
				.createNativeQuery(sql)
				.setFlushMode(FlushModeType.COMMIT)
				.unwrap(NativeQueryImpl.class)
				.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		query.setMaxResults(size);
		query.setFirstResult(start);
		if (args != null) {
			String s;
			for (Iterator iterator = args.keySet().iterator(); iterator.hasNext(); query.setParameter(s, args.get(s))) {
				s = (String) iterator.next();
			}
		}
		return query.getResultList();
	}



}
