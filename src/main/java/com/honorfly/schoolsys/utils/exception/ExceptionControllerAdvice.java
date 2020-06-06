package com.honorfly.schoolsys.utils.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(Exception.class)
    public Result jsonErrorHandler(HttpServletRequest req, Exception e){
        return ResultGenerator.genFailResult(e.getMessage());
    }
}