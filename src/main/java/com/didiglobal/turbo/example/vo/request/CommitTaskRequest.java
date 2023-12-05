package com.didiglobal.turbo.example.vo.request;

import com.didiglobal.turbo.engine.model.InstanceData;
import lombok.Data;

import java.util.List;

/**
 * @author: lijinghao
 * @create: 2023-12-04 14:34
 */
@Data
public class CommitTaskRequest {
    /**
     * 流程实例id
     */
    private String flowInstanceId;
    /**
     * 节点任务实例id
     */
    private String taskInstanceId;
    /**
     * 提交的参数
     */
    private List<InstanceData> variables;
}
