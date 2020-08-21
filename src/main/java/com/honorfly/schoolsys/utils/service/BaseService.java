package com.honorfly.schoolsys.utils.service;

import com.honorfly.schoolsys.entry.SessionUser;
import com.honorfly.schoolsys.entry.SysPermission;
import com.honorfly.schoolsys.utils.AppConfig;
import com.honorfly.schoolsys.utils.dao.EntityObj;
import com.honorfly.schoolsys.utils.dao.IBaseDao;
import com.honorfly.schoolsys.utils.redis.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    protected RedisUtil redisUtil;
    @Autowired
    protected AppConfig appConfig;

    public SessionUser getSession() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SessionUser sessionUser = (SessionUser) auth.getPrincipal();
        return sessionUser;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Long save(EntityObj data) throws Exception {
        data.lastModifiedDate = new Date();
        data.lastModifier = getSession().getUsername();
        data.lastModifierId = getSession().getId();
        data.setCreater(getSession().getUsername());
        data.setCreaterId(getSession().getId());
        data.setAdminId(getSession().getAdminId());
        data.setSchoolId(getSession().getSchoolId());
        baseDaoImpl.save(data);
        return data.id;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Long edit(EntityObj data) throws Exception {
        data.lastModifiedDate = new Date();
        data.lastModifier = getSession().getUsername();
        data.lastModifierId = getSession().getId();
        baseDaoImpl.save(data);
        return data.id;
    }
    /**
     * 逻辑删除
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public <T> void logicDelete(Class<T> clazz, Long id) throws Exception {
        EntityObj entityObj = (EntityObj) this.getById(clazz, id);
        if(!entityObj.getAdminId().equals(getSession().getAdminId())){
            throw new BaseException("非法删除");
        }
        entityObj.setInvalid(false);
        this.save(entityObj);
    }

    /**
     * 删除
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public <T> void delete(Class<T> clazz, Long id) throws Exception {
        EntityObj entityObj = (EntityObj) this.getById(clazz, id);
        baseDaoImpl.delete(entityObj);
    }

    /**
     * 查询实体
     */
    @Override
    @Transactional(readOnly = true)
    public <T> T getById(Class<T> clazz, Object id) throws Exception {
        EntityObj entityObj = (EntityObj) baseDaoImpl.getById(clazz, id);
        if (entityObj == null || !entityObj.invalid) {
            throw new BaseException("没有对应的数据");
        }
        if (!(entityObj instanceof SysPermission) && !getSession().getAdminId().equals(entityObj.getAdminId())){
			throw new BaseException("没有对应的数据");
		}
        return (T) entityObj;
    }

    public int getSQLTotalCnt(String sql)throws Exception {
       return  baseDaoImpl.getSQLTotalCnt(sql);
    }

    public Page getDataPageBySQL(String sql, Class clazz, Map<String, String> args, int curPage, int pageSize)
            throws Exception// 返回视图
    {
        return PageFactory.createPageBySql(baseDaoImpl, sql, args, clazz, curPage, pageSize);
    }

    public Page getMapDataPageBySQL(String sql, Map<String, String> args, int curPage, int pageSize)
            throws Exception// 返回视图
    {
        return PageFactory.createMapPageBySql(baseDaoImpl, sql, args, curPage, pageSize);
    }

    public List loadMapBySQL(final String sql, final Map<String, String> args) throws Exception {
        return baseDaoImpl.loadMapBySQL(sql, args);
    }

    public List loadBySQL(final String sql, final Map<String, String> args, Class clazz) throws Exception {
        return baseDaoImpl.loadBySQL(sql, args, clazz);
    }

    public List loadByJPQL(final String sql, final Map<String, String> args) throws Exception {
        return baseDaoImpl.loadByJPQL(sql, args);
    }

    public Page getMapDataPageByJPQL(String sql, Map<String, String> args, int curPage, int pageSize)
            throws Exception// 返回视图
    {
        return PageFactory.createPageByJPQL(baseDaoImpl, sql, args, curPage, pageSize);
    }
}
