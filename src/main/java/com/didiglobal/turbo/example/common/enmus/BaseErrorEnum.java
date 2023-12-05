package com.didiglobal.turbo.example.common.enmus;

import lombok.Getter;

/**
 * @author: lijinghao
 * @create: 2023-11-01 17:26
 */
public enum BaseErrorEnum {

    SUCCESS(0, "success"),
    FAIL(-1, "fail"),

    INVALID_PARAM(301, "无效参数"),

    SYSTEM_ERROR(500, "系统异常");


    BaseErrorEnum(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    @Getter
    private final int errCode;
    @Getter
    private final String errMsg;

}
