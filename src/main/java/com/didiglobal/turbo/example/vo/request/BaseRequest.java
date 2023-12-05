package com.didiglobal.turbo.example.vo.request;

import lombok.Data;

/**
 * @author: lijinghao
 * @create: 2023-11-30 16:48
 */
@Data
public class BaseRequest {
    private String operator;
    private String tenant;
    private String caller;
}
