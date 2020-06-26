package com.honorfly.schoolsys.utils.securiry;


import java.util.ArrayList;
import java.util.List;

//
public final class AppWhiteList {

	public static  List<String> whiteList = new ArrayList<String>();


	static {
		whiteList.add("/sys/user/info");
		whiteList.add("/sys/user/nav");
	}
}
