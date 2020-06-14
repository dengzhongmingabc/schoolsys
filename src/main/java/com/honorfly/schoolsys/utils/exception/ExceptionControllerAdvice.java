package com.honorfly.schoolsys.utils.exception;

import com.honorfly.schoolsys.utils.Result;
import com.honorfly.schoolsys.utils.ResultGenerator;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(Exception.class)
    public Result jsonErrorHandler(HttpServletRequest req, Exception e){
        e.printStackTrace();
        return ResultGenerator.genFailResult(e.toString());
    }
}