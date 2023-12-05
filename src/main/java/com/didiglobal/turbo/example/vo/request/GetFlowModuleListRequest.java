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
public class GetFlowModuleListRequest extends BaseRequest {

    private String flowModuleId;
    private String flowDeployId;
    private String flowName;
    private Integer current;
    private Integer size;
}
