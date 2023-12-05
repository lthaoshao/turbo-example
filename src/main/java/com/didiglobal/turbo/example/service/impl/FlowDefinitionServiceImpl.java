package com.didiglobal.turbo.example.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.didiglobal.turbo.engine.common.FlowDeploymentStatus;
import com.didiglobal.turbo.engine.dao.FlowDefinitionDAO;
import com.didiglobal.turbo.engine.dao.FlowDeploymentDAO;
import com.didiglobal.turbo.engine.engine.ProcessEngine;
import com.didiglobal.turbo.engine.entity.FlowDefinitionPO;
import com.didiglobal.turbo.engine.entity.FlowDeploymentPO;
import com.didiglobal.turbo.engine.param.CreateFlowParam;
import com.didiglobal.turbo.engine.param.DeployFlowParam;
import com.didiglobal.turbo.engine.param.GetFlowModuleParam;
import com.didiglobal.turbo.engine.param.UpdateFlowParam;
import com.didiglobal.turbo.engine.result.CreateFlowResult;
import com.didiglobal.turbo.engine.result.DeployFlowResult;
import com.didiglobal.turbo.engine.result.FlowModuleResult;
import com.didiglobal.turbo.engine.result.UpdateFlowResult;
import com.didiglobal.turbo.example.common.enmus.FlowModuleStatusEnum;
import com.didiglobal.turbo.example.service.FlowDefinitionService;
import com.didiglobal.turbo.example.vo.request.*;
import com.didiglobal.turbo.example.vo.response.FlowModuleListResponse;
import com.didiglobal.turbo.example.vo.response.FlowModuleResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 流程定义服务处理
 * PS: 因为Turbo中的的定义表包含的信息有限, 我们可以根据自身的业务特性, 在引擎之上的业务层新增一张流程表来记录其他信息
 *      如, 新增 t_flow, 增加流程的名称\版本\状态\类型\业务信息以及初始化动作等等
 *      届时, 引擎层将只作底层引擎, 业务层仅通过 processEngine 完成流程的定义和查询等操作
 *
 * @author: lijinghao
 * @create: 2023-11-27 20:37
 */
@Slf4j
@Service
public class FlowDefinitionServiceImpl implements FlowDefinitionService {

    @Resource
    private ProcessEngine processEngine;

    @Resource
    private FlowDefinitionDAO flowDefinitionDAO;

    @Resource
    private FlowDeploymentDAO flowDeploymentDAO;

    /**
     * 创建流程
     *
     * @param request 创建流程基础信息
     * @return 创建流程结果
     */
    @Override
    public CreateFlowResult createFlow(CreateFlowRequest request) {
        CreateFlowParam param = new CreateFlowParam(request.getTenant(), request.getCaller());
        BeanUtils.copyProperties(request, param);
        return processEngine.createFlow(param);
    }

    /**
     * 保存流程模型数据
     *
     * @param request 保存流程模型请求参数
     * @return 保存流程模型结果
     */
    @Override
    public UpdateFlowResult updateFlow(UpdateFlowRequest request) {
        UpdateFlowParam param = new UpdateFlowParam(request.getTenant(), request.getCaller());
        BeanUtils.copyProperties(request, param);
        return processEngine.updateFlow(param);
    }

    /**
     * 发布流程
     *
     * @param request 发布流程请求参数
     * @return 发布流程结果
     */
    @Override
    public DeployFlowResult deployFlow(DeployFlowRequest request) {
        DeployFlowParam param = new DeployFlowParam(request.getTenant(), request.getCaller());
        BeanUtils.copyProperties(request, param);
        return processEngine.deployFlow(param);
    }

    /**
     * 获取单个流程
     *
     * @param request 查询单个流程参数
     * @return 查询单个流程结果
     */
    @Override
    public FlowModuleResult getFlowModule(GetFlowModuleRequest request) {
        GetFlowModuleParam param = new GetFlowModuleParam();
        BeanUtils.copyProperties(request, param);
        return processEngine.getFlowModule(param);
    }

    /**
     * 查询流程列表
     *
     * @param getFlowModuleListRequest 查询流程列表参数
     * @return 查询流程结果
     */
    @Override
    public FlowModuleListResponse getFlowModuleList(GetFlowModuleListRequest getFlowModuleListRequest) {

        Page<FlowDefinitionPO> page = buildGetFlowModuleListPage(getFlowModuleListRequest);
        QueryWrapper<FlowDefinitionPO> queryWrapper = buildGetFlowModuleListQueryWrapper(getFlowModuleListRequest);
        IPage<FlowDefinitionPO> pageRes = flowDefinitionDAO.page(page, queryWrapper);

        List<FlowModuleResponse> flowModuleList = pageRes.getRecords().stream().map(po -> {
            FlowModuleResponse response = new FlowModuleResponse();
            BeanUtils.copyProperties(po, response);
            int count = flowDeploymentDAO.count(buildCountFlowDeployQueryWrapper(po.getFlowModuleId()));
            if (count >= 1) {
                response.setStatus(FlowModuleStatusEnum.PUBLISHED.getValue());
            }
            return response;
        }).collect(Collectors.toList());

        FlowModuleListResponse flowModuleListResponse = new FlowModuleListResponse();
        flowModuleListResponse.setFlowModuleList(flowModuleList);
        BeanUtils.copyProperties(pageRes, flowModuleListResponse);
        return flowModuleListResponse;
    }

    private Page<FlowDefinitionPO> buildGetFlowModuleListPage(GetFlowModuleListRequest getFlowModuleListRequest) {
        Page<FlowDefinitionPO> page = new Page<>();
        if (getFlowModuleListRequest.getSize() != null && getFlowModuleListRequest.getCurrent() != null) {
            page.setCurrent(getFlowModuleListRequest.getCurrent());
            page.setSize(getFlowModuleListRequest.getSize());
        }
        return page;
    }

    private QueryWrapper<FlowDefinitionPO> buildGetFlowModuleListQueryWrapper(GetFlowModuleListRequest getFlowModuleListRequest) {
        QueryWrapper<FlowDefinitionPO> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(getFlowModuleListRequest.getFlowModuleId())) {
            queryWrapper.eq("flow_module_id", getFlowModuleListRequest.getFlowModuleId());
        }
        if (StringUtils.isNotBlank(getFlowModuleListRequest.getFlowName())) {
            queryWrapper.like("flow_name", getFlowModuleListRequest.getFlowName());
        }
        if (StringUtils.isNotBlank(getFlowModuleListRequest.getFlowDeployId())) {
            queryWrapper.eq("flow_module_id", getFlowModuleListRequest.getFlowDeployId());
        }
        queryWrapper.orderByDesc("modify_time");
        return queryWrapper;
    }

    private QueryWrapper<FlowDeploymentPO> buildCountFlowDeployQueryWrapper(String flowModuleId) {
        QueryWrapper<FlowDeploymentPO> flowDeployQuery = new QueryWrapper<>();
        flowDeployQuery.eq("flow_module_id", flowModuleId);
        flowDeployQuery.eq("status", FlowDeploymentStatus.DEPLOYED);
        return flowDeployQuery;
    }

}
