package com.didiglobal.turbo.example.exception;


import com.didiglobal.turbo.example.common.enmus.BaseErrorEnum;

/**
 * @author didi
 */
public class BusinessException extends BaseException {

    public BusinessException(int errNo, String errMsg) {
        super(errNo, errMsg);
    }

    public BusinessException(BaseErrorEnum errorEnum) {
        super(errorEnum.getErrCode(), errorEnum.getErrMsg());
    }

    public BusinessException(BaseErrorEnum errorEnum, String msg) {
        super(errorEnum.getErrCode(), msg);
    }

    @Override
    public String getMessage() {
        return getErrMsg();
    }
}
