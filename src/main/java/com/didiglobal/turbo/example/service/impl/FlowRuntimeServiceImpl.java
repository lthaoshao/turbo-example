package com.didiglobal.turbo.example.service.impl;

import com.didiglobal.turbo.engine.engine.ProcessEngine;
import com.didiglobal.turbo.engine.param.CommitTaskParam;
import com.didiglobal.turbo.engine.param.RollbackTaskParam;
import com.didiglobal.turbo.engine.param.StartProcessParam;
import com.didiglobal.turbo.engine.result.*;
import com.didiglobal.turbo.example.service.FlowRuntimeService;
import com.didiglobal.turbo.example.vo.request.CommitTaskRequest;
import com.didiglobal.turbo.example.vo.request.CreateInstanceRequest;
import com.didiglobal.turbo.example.vo.request.HistoryNodeListRequest;
import com.didiglobal.turbo.example.vo.request.RollbackTaskRequest;
import com.didiglobal.turbo.example.vo.response.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 流程执行服务处理
 * <p>
 * 当前示例代码中,仅做了透传,使用方可以自行添加业务处理
 *
 * @author: lijinghao
 * @create: 2023-12-04 11:51
 */
@Slf4j
@Service
public class FlowRuntimeServiceImpl implements FlowRuntimeService {

    @Resource
    private ProcessEngine processEngine;

    /**
     * 创建流程实例,启动流程
     *
     * @param request 指定流程和变量参数
     * @return 创建结果
     */
    @Override
    public CreateInstanceResponse createInstance(CreateInstanceRequest request) {
        StartProcessParam param = buildStartProcessParam(request);
        // 调用引擎创建实例
        StartProcessResult startProcessResult = processEngine.startProcess(param);
        return handleStartProcessResult(startProcessResult);
    }

    /**
     * 提交任务
     *
     * @param request 指定任务
     * @return 执行结果
     */
    @Override
    public CommitTaskResponse commit(CommitTaskRequest request) {
        CommitTaskParam param = buildCommitTaskParam(request);
        CommitTaskResult commitTaskResult = processEngine.commitTask(param);
        return handleCommitTaskResult(commitTaskResult);
    }

    /**
     * 回滚任务
     *
     * @param request 指定任务
     * @return 执行结果
     */
    @Override
    public RollbackTaskResponse rollback(RollbackTaskRequest request) {
        RollbackTaskParam param = buildRollbackTaskParam(request);
        RollbackTaskResult rollbackTaskResult = processEngine.rollbackTask(param);
        return handleRollbackTaskResult(rollbackTaskResult);
    }

    /**
     * 获取历史执行节点列表
     *
     * @param request
     * @return
     */
    @Override
    public HistoryNodeListResponse getHistoryNodeList(HistoryNodeListRequest request) {
        NodeInstanceListResult historyUserTaskList = processEngine.getHistoryUserTaskList(request.getFlowInstanceId());
        return handleHistoryNodeListResponse(historyUserTaskList);
    }

    /**
     * 获取历史全部节点执行列表
     *
     * @param request
     * @return
     */
    @Override
    public HistoryElementListResponse getHistoryElementList(HistoryNodeListRequest request) {
        ElementInstanceListResult historyElementList = processEngine.getHistoryElementList(request.getFlowInstanceId());
        return handleHistoryElementListResponse(historyElementList);
    }

    private HistoryElementListResponse handleHistoryElementListResponse(ElementInstanceListResult historyElementList) {
        HistoryElementListResponse response = new HistoryElementListResponse();
        response.setNodeInstanceList(historyElementList.getElementInstanceList());
        return response;
    }

    private HistoryNodeListResponse handleHistoryNodeListResponse(NodeInstanceListResult historyUserTaskList) {
        HistoryNodeListResponse response = new HistoryNodeListResponse();
        response.setNodeInstanceList(historyUserTaskList.getNodeInstanceList());
        return response;
    }

    private RollbackTaskResponse handleRollbackTaskResult(RollbackTaskResult rollbackTaskResult) {
        RollbackTaskResponse response = new RollbackTaskResponse();
        BeanUtils.copyProperties(rollbackTaskResult, response);
        return response;
    }

    private RollbackTaskParam buildRollbackTaskParam(RollbackTaskRequest request) {
        RollbackTaskParam param = new RollbackTaskParam();
        BeanUtils.copyProperties(request, param);
        return param;
    }

    private CommitTaskResponse handleCommitTaskResult(CommitTaskResult commitTaskResult) {
        CommitTaskResponse response = new CommitTaskResponse();
        BeanUtils.copyProperties(commitTaskResult, response);
        return response;
    }

    private CommitTaskParam buildCommitTaskParam(CommitTaskRequest request) {
        CommitTaskParam param = new CommitTaskParam();
        BeanUtils.copyProperties(request, param);
        return param;
    }

    private CreateInstanceResponse handleStartProcessResult(StartProcessResult startProcessResult) {
        CreateInstanceResponse response = new CreateInstanceResponse();
        // 对于结果\错误码等也可以做处理
        BeanUtils.copyProperties(startProcessResult, response);
        return response;
    }

    private StartProcessParam buildStartProcessParam(CreateInstanceRequest request) {
        // 如果存在业务层Flow信息, 可先做验证在构建
        StartProcessParam param = new StartProcessParam();
        BeanUtils.copyProperties(request, param);
        return param;
    }
}
