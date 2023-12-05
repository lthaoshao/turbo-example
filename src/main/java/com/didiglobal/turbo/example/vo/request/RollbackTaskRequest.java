package com.didiglobal.turbo.example.vo.request;

import lombok.Data;

/**
 * @author: lijinghao
 * @create: 2023-12-04 15:00
 */
@Data
public class RollbackTaskRequest {
    /**
     * 流程实例id
     */
    private String flowInstanceId;
    /**
     * 节点任务实例id
     */
    private String taskInstanceId;
}
