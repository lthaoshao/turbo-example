package com.didiglobal.turbo.example.service;

import com.didiglobal.turbo.example.vo.request.CommitTaskRequest;
import com.didiglobal.turbo.example.vo.request.CreateInstanceRequest;
import com.didiglobal.turbo.example.vo.request.HistoryNodeListRequest;
import com.didiglobal.turbo.example.vo.request.RollbackTaskRequest;
import com.didiglobal.turbo.example.vo.response.*;

/**
 * @author: lijinghao
 * @create: 2023-12-04 11:51
 */
public interface FlowRuntimeService {

    /**
     * 创建流程实例,启动流程
     *
     * @param request 指定流程和变量参数
     * @return 创建结果
     */
    CreateInstanceResponse createInstance(CreateInstanceRequest request);

    /**
     * 提交任务
     *
     * @param request 指定任务
     * @return 执行结果
     */
    CommitTaskResponse commit(CommitTaskRequest request);

    /**
     * 回滚任务
     *
     * @param request 指定任务
     * @return 执行结果
     */
    RollbackTaskResponse rollback(RollbackTaskRequest request);

    /**
     * 获取历史用户节点执行列表
     *
     * @param request
     * @return
     */
    HistoryNodeListResponse getHistoryNodeList(HistoryNodeListRequest request);

    /**
     * 获取历史全部节点执行列表
     *
     * @param request
     * @return
     */
    HistoryElementListResponse getHistoryElementList(HistoryNodeListRequest request);

    // 其他的功能, 可以根据需要来实现
}
