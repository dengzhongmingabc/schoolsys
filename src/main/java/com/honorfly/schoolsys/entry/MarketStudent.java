package com.honorfly.schoolsys.entry;

import com.honorfly.schoolsys.utils.dao.EntityObj;

/**
 * SysUser entity. @author MyEclipse Persistence Tools
 */

public class MarketStudent extends EntityObj {

	private String name;

	private String mobile;

	private String personType;//母亲，父亲，自己

	private int sex;//1男，2女 ，3未知

	private String introduce;//介绍人


	private int seek;//1来电。2来访，3网络，其它

	private int seekDepth;//1,2,3,4,5

}
