package com.didiglobal.turbo.example.vo.request;

import com.didiglobal.turbo.engine.model.InstanceData;
import lombok.Data;

import java.util.List;

/**
 * @author: lijinghao
 * @create: 2023-12-04 11:29
 */
@Data
public class CreateInstanceRequest {

    /**
     * 流程模型id
     */
    private String flowModuleId;
    /**
     * 流程部署id
     */
    private String flowDeployId;
    /**
     * 变量参数
     */
    private List<InstanceData> variables;
}
