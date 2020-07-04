package com.honorfly.schoolsys.utils;


import com.honorfly.schoolsys.entry.SessionUser;
import com.honorfly.schoolsys.utils.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SqlUtils {

    @Autowired
    protected RedisUtil redisUtil;
    @Autowired
    protected AppConfig appConfig;

    private String selectFrom;

    private String where = " where 1=1 ";

    private String whereSelf;

    private String commentWhere;

    private String orderBy;


    public SqlUtils setSelectFrom(String selectFrom) {
        this.selectFrom = selectFrom;
        return this;
    }

    public SqlUtils setWhere() {
        this.where = " where 1=1 ";
        return this;
    }

    public SqlUtils setWhereSelf(String sqlWhere) {
        this.whereSelf = sqlWhere;
        return this;
    }

    public SqlUtils setCommentWhere() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SessionUser sessionUser = (SessionUser) auth.getPrincipal();
        SessionUser redisSession = (SessionUser) redisUtil.get(AppConst.Redis_Session_Namespace + sessionUser.getId());
        StringBuffer sqlWhere = new StringBuffer();
        sqlWhere.append(" and invalid = true ");
        sqlWhere.append(" and admin_id = " + redisSession.getAdminId() + " ");
        sqlWhere.append(" and school_id = " + redisSession.getSchoolId() + " ");
        this.commentWhere = sqlWhere.toString();
        return this;
    }


    public SqlUtils setOrderBy() {
        this.orderBy = " order by id desc ";
        return this;
    }

    public SqlUtils setOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public String build() {
        StringBuffer sql = new StringBuffer();
        sql.append(selectFrom)
                .append(where)
                .append(commentWhere)
                .append(orderBy);
        return sql.toString();
    }

    public static void main(String[] args) {
        String sql = new SqlUtils().setSelectFrom("select * from school")
                .setWhere()
                .setCommentWhere()
                .setOrderBy()
                .build();
        System.out.println(sql);
    }

    public String getCommentWhere() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SessionUser sessionUser = (SessionUser) auth.getPrincipal();
        SessionUser redisSession = (SessionUser) redisUtil.get(AppConst.Redis_Session_Namespace + sessionUser.getId());
        StringBuffer sqlWhere = new StringBuffer();
        sqlWhere.append(" and invalid = true ");
        sqlWhere.append(" and admin_id = " + redisSession.getAdminId() + " ");
        sqlWhere.append(" and school_id = " + redisSession.getSchoolId() + " ");
        return sqlWhere.toString();
    }

}
