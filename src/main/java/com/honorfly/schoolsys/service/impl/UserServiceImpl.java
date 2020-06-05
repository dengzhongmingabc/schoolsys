package com.honorfly.schoolsys.service.impl;

import com.honorfly.schoolsys.service.IUserService;
import com.honorfly.schoolsys.entry.User;
import com.honorfly.schoolsys.utils.service.BaseService;
import com.honorfly.schoolsys.utils.service.Page;
import com.honorfly.schoolsys.utils.service.PageFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class UserServiceImpl extends BaseService implements IUserService {

    @Override
    public User queryByUserName(String userName) throws Exception{

        Map<String,String> args = new HashMap<String,String>();
        args.put("userName",userName);
        List ret  = baseDaoImpl.loadBySQL("select u.* from sys_user u where u.user_name=:userName",args,User.class);
        if(ret == null || ret.isEmpty())
            return null;
        return (User)ret.get(0);
    }


    public Page queryUserPageList(Map<String,String> where,int currentPage,int pageSize)  throws Exception {
        StringBuffer sbsql = new StringBuffer();
        sbsql.append("select * from user where 1=1 ");
        Map args = new HashMap();
        if(!StringUtils.isBlank(where.get("username"))){
            sbsql.append(" and  user_name like :username");
            args.put("username", "%"+where.get("username")+"%");
        }
        sbsql.append(" order by id");
        return PageFactory.createPageBySql(baseDaoImpl, sbsql.toString(), args,User.class, currentPage, pageSize);
    }

}