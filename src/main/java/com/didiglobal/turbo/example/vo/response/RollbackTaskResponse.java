package com.didiglobal.turbo.example.vo.response;

import com.didiglobal.turbo.engine.bo.NodeInstance;
import com.didiglobal.turbo.engine.model.InstanceData;
import lombok.Data;

import java.util.List;

/**
 * 回滚响应
 *
 * @author: lijinghao
 * @create: 2023-12-04 15:00
 */
@Data
public class RollbackTaskResponse {

    private String flowInstanceId;
    private int status;
    private NodeInstance activeTaskInstance;
    private List<InstanceData> variables;
}
