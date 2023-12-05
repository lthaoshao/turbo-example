package com.didiglobal.turbo.example.controller;

import com.didiglobal.turbo.example.service.FlowRuntimeService;
import com.didiglobal.turbo.example.vo.request.CommitTaskRequest;
import com.didiglobal.turbo.example.vo.request.CreateInstanceRequest;
import com.didiglobal.turbo.example.vo.request.HistoryNodeListRequest;
import com.didiglobal.turbo.example.vo.request.RollbackTaskRequest;
import com.didiglobal.turbo.example.vo.response.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 流程执行
 *
 * @author: lijinghao
 * @create: 2023-11-23 19:46
 */
@Slf4j
@RestController
@RequestMapping("/turbo")
public class FlowRuntimeController {

    @Resource
    private FlowRuntimeService flowRuntimeService;

    /**
     * 创建流程实例
     *
     * @param request 指定流程和参数
     * @return 创建结果
     */
    @PostMapping("/createInstance")
    public Response<CreateInstanceResponse> createInstance(@RequestBody CreateInstanceRequest request) {
        // todo check params
        CreateInstanceResponse response = flowRuntimeService.createInstance(request);
        return Response.make(response);
    }

    /**
     * 提交用户任务
     *
     * @param request 指定任务
     * @return 执行结果
     */
    @PostMapping("/commitTask")
    public Response<CommitTaskResponse> commitTask(@RequestBody CommitTaskRequest request) {
        // todo check params
        CommitTaskResponse response = flowRuntimeService.commit(request);
        return Response.make(response);
    }

    /**
     * 回滚用户任务
     *
     * @param request 指定任务
     * @return 执行结果
     */
    @PostMapping("/rollbackTask")
    public Response<RollbackTaskResponse> rollbackTask(@RequestBody RollbackTaskRequest request) {
        // todo check params
        RollbackTaskResponse response = flowRuntimeService.rollback(request);
        return Response.make(response);
    }

    /**
     * 用户任务历史列表
     *
     * @param request 指定任务
     * @return 执行结果
     */
    @PostMapping("/history/userTasks")
    public Response<HistoryNodeListResponse> getHistoryNodeList(@RequestBody HistoryNodeListRequest request) {
        // todo check params
        HistoryNodeListResponse response = flowRuntimeService.getHistoryNodeList(request);
        return Response.make(response);
    }

    /**
     * 用户任务历史列表
     *
     * @param request 指定任务
     * @return 执行结果
     */
    @PostMapping("/history/elements")
    public Response<HistoryElementListResponse> getHistoryElementList(@RequestBody HistoryNodeListRequest request) {
        // todo check params
        HistoryElementListResponse response = flowRuntimeService.getHistoryElementList(request);
        return Response.make(response);
    }
}
