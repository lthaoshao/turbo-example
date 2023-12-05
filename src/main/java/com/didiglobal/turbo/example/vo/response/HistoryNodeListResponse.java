package com.didiglobal.turbo.example.vo.response;

import com.didiglobal.turbo.engine.bo.NodeInstance;
import lombok.Data;

import java.util.List;

/**
 * @author: lijinghao
 * @create: 2023-12-05 16:06
 */
@Data
public class HistoryNodeListResponse {
    private List<NodeInstance> nodeInstanceList;
}
