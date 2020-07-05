package com.honorfly.schoolsys.utils;

public enum ResultCode {
    SUCCESS(200),//成功
    FAIL(400),//失败
    NEED_LOGIN(405),//需要登录
    LOGIN_FAIL(401),//登录失败
    ARGS_ERR(402),//参数错误
    FORBIDDEN_ERR(403),//没有权限
    NOT_FOUND(404),//接口不存在
    INTERNAL_SERVER_ERROR(500);//服务器内部错误

    private final int code;

    ResultCode(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }
}

