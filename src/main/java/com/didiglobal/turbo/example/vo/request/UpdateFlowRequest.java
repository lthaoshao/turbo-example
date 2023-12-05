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
public class UpdateFlowRequest extends BaseRequest{
    private String flowKey;
    private String flowName;
    private String flowModuleId;
    private String flowModel;
    private String remark;
}
