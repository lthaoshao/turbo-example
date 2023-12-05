package com.didiglobal.turbo.example.exception;


import com.didiglobal.turbo.example.common.enmus.BaseErrorEnum;

/**
 * @author didi
 */
public class ParamException extends BaseException {

    public ParamException(Integer errNo, String errMsg) {
        super(errNo, errMsg);
    }

    public ParamException(BaseErrorEnum errorEnum) {
        super(errorEnum.getErrCode(), errorEnum.getErrMsg());
    }

    @Override
    public String getMessage() {
        return getErrMsg();
    }
}
