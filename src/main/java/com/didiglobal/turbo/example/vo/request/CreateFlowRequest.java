package com.didiglobal.turbo.example.vo.request;

import com.didiglobal.turbo.engine.model.InstanceData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * @author didi
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CreateFlowRequest extends BaseRequest {
    private String flowName;
    private String flowKey;
    private String remark;
    private String flowModuleId;
    private String flowDeployId;
    private List<InstanceData> variables;
}
