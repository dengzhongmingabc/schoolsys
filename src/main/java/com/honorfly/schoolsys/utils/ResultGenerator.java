package com.honorfly.schoolsys.utils;

public class ResultGenerator {

    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    public static Result genSuccessResult() {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setSuccess(true)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    public static Result genSuccessResult(Object data) {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setSuccess(true)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setResult(data);
    }

    public static Result genFailResult(String message) {
        return new Result()
                .setCode(ResultCode.FAIL)
                .setSuccess(false)
                .setMessage(message);
    }

    public static Result genFailResult(ResultCode msgCode,String message) {
        return new Result()
                .setCode(msgCode)
                .setSuccess(false)
                .setMessage(message);
    }
}
