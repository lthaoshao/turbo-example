package com.didiglobal.turbo.example.vo.response;

import com.didiglobal.turbo.engine.bo.ElementInstance;
import lombok.Data;

import java.util.List;

/**
 * @author: lijinghao
 * @create: 2023-12-05 16:06
 */
@Data
public class HistoryElementListResponse {
    private List<ElementInstance> nodeInstanceList;
}
