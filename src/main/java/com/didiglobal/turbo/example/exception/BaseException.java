package com.didiglobal.turbo.example.exception;

import com.didiglobal.turbo.example.common.enmus.BaseErrorEnum;

import java.text.MessageFormat;
import java.util.StringJoiner;

/**
 * @author didi
 */
public class BaseException extends RuntimeException {

    private static final String ERROR_MSG_FORMAT = "{0}({1})";

    private int errCode;
    private String errMsg;

    public BaseException(int errNo, String errMsg) {
        super(errMsg);
        this.errCode = errNo;
        this.errMsg = errMsg;
    }

    public BaseException(BaseErrorEnum errorEnum) {
        super(errorEnum.getErrMsg());
        this.errCode = errorEnum.getErrCode();
        this.errMsg = errorEnum.getErrMsg();
    }

    public BaseException(BaseErrorEnum errorEnum, String detailMsg) {
        super(errorEnum.getErrMsg());
        String errMsg = MessageFormat.format(ERROR_MSG_FORMAT, errorEnum.getErrMsg(), detailMsg);
        this.errCode = errorEnum.getErrCode();
        this.errMsg = errMsg;
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

    @Override
    public String toString() {
        return new StringJoiner(", ", BaseException.class.getSimpleName() + "[", "]")
                .add("errNo=" + errCode)
                .add("errMsg='" + errMsg + "'")
                .toString();
    }
}
