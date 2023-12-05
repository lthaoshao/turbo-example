package com.didiglobal.turbo.example.controller;

import com.didiglobal.turbo.example.common.enmus.BaseErrorEnum;
import com.didiglobal.turbo.example.exception.BusinessException;
import com.didiglobal.turbo.example.exception.ParamException;
import com.didiglobal.turbo.example.vo.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * @author didi
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class ControllerAdviceHandler {

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public Response handlerMethodViolationException(HttpServletRequest req, MissingServletRequestParameterException me) {
        String errMsg = me.getMessage();
        return Response.fail(BaseErrorEnum.INVALID_PARAM, StringUtils.defaultString(errMsg, " missing params"));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = Exception.class)
    public Response defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        return Response.fail(BaseErrorEnum.FAIL);
    }

    @ExceptionHandler(value = BusinessException.class)
    public Response businessExceptionHandler(HttpServletRequest req, BusinessException be) throws Exception {
        return Response.exception(be);
    }

    @ExceptionHandler(value = ParamException.class)
    public Response paramExceptionHandler(HttpServletRequest req, ParamException pe) throws Exception {
        return Response.exception(pe);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Response convertExceptionHandler(HttpServletRequest req, HttpMessageNotReadableException he) {
        return Response.fail(BaseErrorEnum.FAIL, "param2enum error");
    }

}
