package com.didiglobal.turbo.example.controller;

import com.didiglobal.turbo.engine.result.CreateFlowResult;
import com.didiglobal.turbo.engine.result.DeployFlowResult;
import com.didiglobal.turbo.engine.result.FlowModuleResult;
import com.didiglobal.turbo.engine.result.UpdateFlowResult;
import com.didiglobal.turbo.example.service.FlowDefinitionService;
import com.didiglobal.turbo.example.vo.request.*;
import com.didiglobal.turbo.example.vo.response.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 流程定义
 *
 * @author: lijinghao
 * @create: 2023-11-23 19:45
 */
@Slf4j
@RestController
@RequestMapping("/logicFlow")
public class FlowDefinitionController {

    @Resource
    private FlowDefinitionService flowService;

    /**
     * 创建流程
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/createFlow", method = {RequestMethod.POST})
    public Response<CreateFlowResponse> createFlow(@RequestBody CreateFlowRequest request) {
        CreateFlowResult createFlowResult = flowService.createFlow(request);
        CreateFlowResponse createFlowResponse = new CreateFlowResponse();
        Response<CreateFlowResponse> baseResponse = Response.make(createFlowResponse);
        BeanUtils.copyProperties(createFlowResult, baseResponse);
        BeanUtils.copyProperties(createFlowResult, createFlowResponse);
        return baseResponse;
    }

    /**
     * 保存流程模型数据
     *
     * @param updateFlowRequest flowModuleId 使用createFlow返回的flowModuleId
     * @return 成功 or 失败
     */
    @RequestMapping(value = "/saveFlowModel", method = {RequestMethod.POST})
    public Response<String> saveFlowModel(@RequestBody UpdateFlowRequest updateFlowRequest) {
        UpdateFlowResult updateFlowResult = flowService.updateFlow(updateFlowRequest);
        Response<String> baseResponse = Response.make(null);
        BeanUtils.copyProperties(updateFlowResult, baseResponse);
        return baseResponse;
    }

    /**
     * 发布流程
     *
     * @param deployFlowRequest flowModuleId 使用createFlow返回的flowModuleId
     * @return
     */
    @RequestMapping(value = "/deployFlow", method = {RequestMethod.POST})
    public Response<DeployFlowResponse> deployFlow(@RequestBody DeployFlowRequest deployFlowRequest) {
        DeployFlowResult deployFlowResult = flowService.deployFlow(deployFlowRequest);
        DeployFlowResponse deployFlowResponse = new DeployFlowResponse();
        Response<DeployFlowResponse> response = Response.make(deployFlowResponse);
        BeanUtils.copyProperties(deployFlowResult, response);
        BeanUtils.copyProperties(deployFlowResult, deployFlowResponse);
        return response;
    }


    /**
     * 查询流程
     *
     * @param getFlowModuleRequest 单个流程请求参数
     * @return 单个流程数据
     */
    @RequestMapping(value = "/queryFlow", method = {RequestMethod.POST})
    public Response<GetFlowModuleResponse> queryFlow(@RequestBody GetFlowModuleRequest getFlowModuleRequest) {
        FlowModuleResult flowModuleResult = flowService.getFlowModule(getFlowModuleRequest);
        GetFlowModuleResponse getFlowModuleResponse = new GetFlowModuleResponse();
        Response<GetFlowModuleResponse> response = Response.make(getFlowModuleResponse);
        BeanUtils.copyProperties(flowModuleResult, response);
        BeanUtils.copyProperties(flowModuleResult, getFlowModuleResponse);
        return response;
    }


    /**
     * 分页查询流程列表
     *
     * @param getFlowModuleListRequest 分页请求参数
     * @return 返回列表数据
     */
    @RequestMapping(value = "/queryFlowList", method = {RequestMethod.POST})
    public Response<FlowModuleListResponse> queryFlowList(@RequestBody GetFlowModuleListRequest getFlowModuleListRequest) {
        FlowModuleListResponse flowModuleListResponse = flowService.getFlowModuleList(getFlowModuleListRequest);
        return Response.make(flowModuleListResponse);
    }

}
