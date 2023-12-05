package com.didiglobal.turbo.example.service;

import com.didiglobal.turbo.engine.result.CreateFlowResult;
import com.didiglobal.turbo.engine.result.DeployFlowResult;
import com.didiglobal.turbo.engine.result.FlowModuleResult;
import com.didiglobal.turbo.engine.result.UpdateFlowResult;
import com.didiglobal.turbo.example.vo.request.*;
import com.didiglobal.turbo.example.vo.response.CreateInstanceResponse;
import com.didiglobal.turbo.example.vo.response.FlowModuleListResponse;

/**
 * @author: lijinghao
 * @create: 2023-11-27 20:37
 */
public interface FlowDefinitionService {

    /**
     * 创建流程
     *
     * @param createFlowParam 创建流程基础信息
     * @return 创建流程结果
     */
    CreateFlowResult createFlow(CreateFlowRequest createFlowParam);

    /**
     * 保存流程模型数据
     *
     * @param updateFlowRequest 保存流程模型请求参数
     * @return 保存流程模型结果
     */
    UpdateFlowResult updateFlow(UpdateFlowRequest updateFlowRequest);

    /**
     * 发布流程
     *
     * @param deployFlowRequest 发布流程请求参数
     * @return 发布流程结果
     */
    DeployFlowResult deployFlow(DeployFlowRequest deployFlowRequest);

    /**
     * 获取单个流程
     *
     * @param getFlowModuleRequest 查询单个流程参数
     * @return 查询单个流程结果
     */
    FlowModuleResult getFlowModule(GetFlowModuleRequest getFlowModuleRequest);

    /**
     * 查询流程列表
     *
     * @param getFlowModuleListRequest 查询流程列表参数
     * @return 查询流程结果
     */
    FlowModuleListResponse getFlowModuleList(GetFlowModuleListRequest getFlowModuleListRequest);

}
