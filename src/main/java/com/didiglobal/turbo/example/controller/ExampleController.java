package com.didiglobal.turbo.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.didiglobal.turbo.engine.bo.NodeInstance;
import com.didiglobal.turbo.engine.model.InstanceData;
import com.didiglobal.turbo.example.common.enmus.BaseErrorEnum;
import com.didiglobal.turbo.example.vo.response.CommitTaskResponse;
import com.didiglobal.turbo.example.vo.response.CreateInstanceResponse;
import com.didiglobal.turbo.example.vo.response.Response;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用作实例, 一次性推进整个流程
 *
 * @author: lijinghao
 * @create: 2023-12-04 15:21
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class ExampleController {

    private static final String DEF_FLOW_MODULE_ID = "404093c0-928a-11ee-83e5-6e24f1631864";
    private static final String BASE_URL = "http://localhost:8080";
    private static final String CREATE_INSTANCE_URL = BASE_URL + "/turbo/createInstance";
    private static final String COMMIT_URL = BASE_URL + "/turbo/commitTask";
    private static final String ROLLBACK_URL = BASE_URL + "/turbo/rollbackTask";
    private static final String HISTORY_URL = BASE_URL + "/turbo/history/elements";

    @Resource
    private RestTemplate restTemplate;

    @RequestMapping("/execute")
    public Response doTest() {
        log.info("准备执行流程...");

        String flowModuleId = DEF_FLOW_MODULE_ID;

        JSONObject request = new JSONObject();
        request.put("flowModuleId", flowModuleId);
        // request.put("flowDeployId", ""); // 需要指定部署id时可设置, 否则取最新的部署id
        List<InstanceData> variables = Lists.newArrayList();
        variables.add(new InstanceData("now_time", "long", System.currentTimeMillis()));
        variables.add(new InstanceData("random_string", "string", RandomStringUtils.random(16)));
        request.put("variables", variables);

        ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(CREATE_INSTANCE_URL, request, JSONObject.class);
        JSONObject body = responseEntity.getBody();
        if (body == null) {
            log.warn("创建流程失败, 请求结果为空");
            return Response.fail(BaseErrorEnum.FAIL, "创建流程失败, 请求结果为空");
        }

        int errCode = body.getIntValue("errCode");
        if (errCode != 0) {
            log.warn("创建流程失败,errCode={}", errCode);
            return Response.fail(BaseErrorEnum.FAIL, body.getString("errMsg"));
        }

        // 创建成功, 待提交
        if (body.getJSONObject("data") == null) {
            log.warn("创建流程失败, 请求结果为空");
            return Response.fail(BaseErrorEnum.FAIL, "创建流程失败");
        }
        JSONObject data = body.getJSONObject("data");
        JSONObject activeTaskInstance = data.getJSONObject("activeTaskInstance");
        String flowInstanceId = data.getString("flowInstanceId");
        String nodeKey = activeTaskInstance.getString("modelKey");
        String nodeName = activeTaskInstance.getString("modelName");
        String nodeInstanceId = activeTaskInstance.getString("nodeInstanceId");
        log.info("流程创建成功.||flowInstanceId={}||taskId={}||nodeKey={}||nodeName={}", flowInstanceId, nodeInstanceId, nodeKey, nodeName);

        // 提交 task 1
        JSONObject commitRequest = new JSONObject();
        commitRequest.put("flowInstanceId", flowInstanceId);
        commitRequest.put("taskInstanceId", nodeInstanceId);
        ResponseEntity<JSONObject> commitResponse = restTemplate.postForEntity(COMMIT_URL, commitRequest, JSONObject.class);

        body = commitResponse.getBody();
        if (body == null) {
            log.warn("第1次提交流程失败, 请求结果为空");
            return Response.fail(BaseErrorEnum.FAIL, "提交流程失败, 请求结果为空");
        }

        errCode = body.getIntValue("errCode");
        if (errCode != 0) {
            log.warn("第1次提交流程失败,errCode={}", errCode);
            return Response.fail(BaseErrorEnum.FAIL, body.getString("errMsg"));
        }

        data = body.getJSONObject("data");
        activeTaskInstance = data.getJSONObject("activeTaskInstance");
        flowInstanceId = data.getString("flowInstanceId");
        nodeKey = activeTaskInstance.getString("modelKey");
        nodeName = activeTaskInstance.getString("modelName");
        nodeInstanceId = activeTaskInstance.getString("nodeInstanceId");

        log.info("第1次流程提交成功.||flowInstanceId={}||taskId={}||nodeKey={}||nodeName={}", flowInstanceId, nodeInstanceId, nodeKey, nodeName);

        // 提交 task2/3
        JSONObject commitRequest2 = new JSONObject();
        commitRequest2.put("flowInstanceId", flowInstanceId);
        commitRequest2.put("taskInstanceId", nodeInstanceId);
        ResponseEntity<JSONObject> commitResponse2 = restTemplate.postForEntity(COMMIT_URL, commitRequest2, JSONObject.class);


        body = commitResponse2.getBody();
        if (body == null) {
            log.warn("第2次提交流程失败, 请求结果为空");
            return Response.fail(BaseErrorEnum.FAIL, "创建流程失败, 请求结果为空");
        }

        errCode = body.getIntValue("errCode");
        if (errCode != 0) {
            log.warn("第2次提交流程失败,errCode={}", errCode);
            return Response.fail(BaseErrorEnum.FAIL, body.getString("errMsg"));
        }

        data = body.getJSONObject("data");
        activeTaskInstance = data.getJSONObject("activeTaskInstance");
        flowInstanceId = data.getString("flowInstanceId");
        nodeKey = activeTaskInstance.getString("modelKey");
        nodeName = activeTaskInstance.getString("modelName");
        nodeInstanceId = activeTaskInstance.getString("nodeInstanceId");

        log.info("第2次流程提交成功.||flowInstanceId={}||taskId={}||nodeKey={}||nodeName={}", flowInstanceId, nodeInstanceId, nodeKey, nodeName);

        log.info("流程执行结束...");
        log.info("获取流程执行记录...");

        JSONObject historyRequest = new JSONObject();
        historyRequest.put("flowInstanceId", flowInstanceId);
        ResponseEntity<JSONObject> historyResult = restTemplate.postForEntity(HISTORY_URL, historyRequest, JSONObject.class);
        JSONObject resultBody = historyResult.getBody();
        if (resultBody == null) {
            log.warn("获取执行列表失败,列表为空");
            return Response.fail(BaseErrorEnum.FAIL, "获取执行列表失败");
        }
        log.info(resultBody.toJSONString());

        return Response.success(DEF_FLOW_MODULE_ID);

    }
}
