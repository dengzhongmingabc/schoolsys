package com.honorfly.schoolsys.service.impl;

import com.honorfly.schoolsys.entry.User;
import com.honorfly.schoolsys.service.ISysManagerService;
import com.honorfly.schoolsys.utils.service.BaseService;
import com.honorfly.schoolsys.utils.service.Page;
import com.honorfly.schoolsys.utils.service.PageFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;


@Service
@Transactional
public class SysManagerServiceImpl extends BaseService implements ISysManagerService {

    @Override
    public Page querySysPermissionPageList(Map<String, String> where, int currentPage, int pageSize) throws Exception {
        StringBuffer sbsql = new StringBuffer();
        sbsql.append("select * from sys_permission where 1=1 ");
        Map args = new HashMap();
        if(!StringUtils.isBlank(where.get("username"))){
            sbsql.append(" and  user_name like :username");
            args.put("username", "%"+where.get("username")+"%");
        }
        sbsql.append(" order by id");
        return PageFactory.createPageBySql(baseDaoImpl, sbsql.toString(), args,User.class, currentPage, pageSize);
    }
}
