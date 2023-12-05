package com.didiglobal.turbo.example.vo.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author didi
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DeployFlowRequest extends BaseRequest {
    private String flowModuleId;
}
