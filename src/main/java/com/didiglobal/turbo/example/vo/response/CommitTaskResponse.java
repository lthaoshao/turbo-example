package com.didiglobal.turbo.example.vo.response;

import com.didiglobal.turbo.engine.bo.NodeInstance;
import com.didiglobal.turbo.engine.model.InstanceData;
import lombok.Data;

import java.util.List;

/**
 * @author: lijinghao
 * @create: 2023-12-04 14:34
 */
@Data
public class CommitTaskResponse {
    private String flowInstanceId;
    private int status;
    private NodeInstance activeTaskInstance;
    private List<InstanceData> variables;
}
