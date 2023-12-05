package com.didiglobal.turbo.example.vo.response;

import com.didiglobal.turbo.example.common.enmus.BaseErrorEnum;
import com.didiglobal.turbo.example.exception.BaseException;

/**
 * @author didi
 */
public class Response<T> {

    int errCode;
    String errMsg;
    T data;


    public Response(BaseErrorEnum errorEnum) {
        this.errCode = errorEnum.getErrCode();
        this.errMsg = errorEnum.getErrMsg();
    }

    public Response(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public Response(BaseErrorEnum errorEnum, T data) {
        this.data = data;
    }

    public static <T> Response<T> success() {
        return new Response<T>(BaseErrorEnum.SUCCESS);
    }

    public static <T> Response<T> success(T o) {
        return new Response<T>(BaseErrorEnum.SUCCESS, o);
    }

    public static <T> Response<T> fail(BaseErrorEnum errorEnum) {
        return new Response<>(errorEnum);
    }

    public static Response fail(BaseErrorEnum errorEnum, String errmsg) {
        return new Response(errorEnum, errmsg);
    }

    public static Response exception(BaseException e) {
        return new Response(e.getErrCode(), e.getErrMsg());
    }

    public static Response systemError() {
        return new Response(BaseErrorEnum.SYSTEM_ERROR);
    }

    public static <T> Response<T> make(T data) {
        return (new Response<T>(BaseErrorEnum.SUCCESS)).setData(data);
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public T getData() {
        return data;
    }

    public Response<T> setData(T data) {
        this.data = data;
        return this;
    }
}
