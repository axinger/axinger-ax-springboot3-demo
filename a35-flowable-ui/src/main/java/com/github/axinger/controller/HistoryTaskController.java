package com.github.axinger.controller;

import cn.hutool.core.io.IoUtil;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.image.impl.DefaultProcessDiagramGenerator;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequestMapping("/history")
@RestController
public class HistoryTaskController {


    @Autowired
    private HistoryService historyService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private ProcessEngine processEngine;

    @RequestMapping(value = "/list")
    public Object createHistoricTaskInstanceQuery() {

        List<HistoricTaskInstance> historicTasks = historyService.createHistoricTaskInstanceQuery()
                .finished()
                .list();
        return historicTasks.stream().map(task -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", task.getId());
            map.put("processInstanceId", task.getProcessInstanceId());
            map.put("name", task.getName());
            map.put("assignee", task.getAssignee());
            map.put("description", task.getDescription());
            return map;
        }).collect(Collectors.toSet());
    }

    /**
     * 流程申请 流转图片输入流
     */

    @RequestMapping(value = "/img")
    public void genProcessDiagram(HttpServletResponse response, String processId) throws Exception {


        String procDefId;
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processId)
                .singleResult();
        if (processInstance == null) {
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processId).singleResult();
            procDefId = historicProcessInstance.getProcessDefinitionId();
        } else {
            procDefId = processInstance.getProcessDefinitionId();
        }


        BpmnModel bpmnModel = repositoryService.getBpmnModel(procDefId);
        String imageType = "png"; // 生成图片的类型
        List<String> highLightedActivities = new ArrayList<>(); // 高亮节点集合
        List<String> highLightedFlows = new ArrayList<>(); // 高亮连线集合
        List<HistoricActivityInstance> hisActInsList = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processId)
                .list(); // 查询所有历史节点信息
        hisActInsList.forEach(historicActivityInstance -> {
            // 遍历
            if ("sequenceFlow".equals(historicActivityInstance.getActivityType())) {
                // 添加高亮连线
                highLightedFlows.add(historicActivityInstance.getActivityId());
            } else {
                // 添加高亮节点
                highLightedActivities.add(historicActivityInstance.getActivityId());
            }
        });
        String activityFontName = "宋体"; // 节点字体
        String labelFontName = "微软雅黑"; // 连线标签字体
        String annotationFontName = "宋体"; // 连线标签字体
        ClassLoader customClassLoader = null; // 类加载器
        double scaleFactor = 1.0d; // 比例因子，默认即可
        boolean drawSequenceFlowNameWithNoLabelDI = true; // 不设置连线标签不会画

        DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator(); // 创建默认的流程图生成器
        // 生成图片
        InputStream inputStream = defaultProcessDiagramGenerator.generateDiagram(bpmnModel, imageType, highLightedActivities
                , highLightedFlows, activityFontName, labelFontName, annotationFontName, customClassLoader,
                scaleFactor, drawSequenceFlowNameWithNoLabelDI); // 获取输入流
        /* try { // 先将图片保存 FileUtils.copyInputStreamToFile(inputStream, new File("E:\", "1.png")); } catch (IOException e) { e.printStackTrace(); } */

        // 直接写到页面，要先获取HttpServletResponse
        // byte[] bytes = IoUtil.readInputStream(inputStream, "flow diagram inputStream");
        //
        //
        //
        // response.setContentType("image/png");
        // ServletOutputStream outputStream = response.getOutputStream();
        // response.reset();
        // outputStream.write(bytes);
        // outputStream.flush();
        // outputStream.close();


        // 将InputStream写入response
        // response.setContentType("application/octet-stream");
        response.setContentType("image/png");
        IoUtil.copy(inputStream, response.getOutputStream());
        response.flushBuffer();

    }

    @RequestMapping(value = "/img2")
    public void img2(HttpServletResponse response, String processId) throws Exception {


        String procDefId;
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processId)
                .singleResult();
        if (processInstance == null) {
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processId).singleResult();
            procDefId = historicProcessInstance.getProcessDefinitionId();
        } else {
            procDefId = processInstance.getProcessDefinitionId();
        }


        // BpmnModel bpmnModel = repositoryService.getBpmnModel(procDefId);
        // DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator(); // 创建默认的流程图生成器
        // String imageType = "png"; // 生成图片的类型
        List<String> highLightedActivities = new ArrayList<>(); // 高亮节点集合
        List<String> highLightedFlows = new ArrayList<>(); // 高亮连线集合
        List<HistoricActivityInstance> hisActInsList = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processId)
                .list(); // 查询所有历史节点信息
        hisActInsList.forEach(historicActivityInstance -> {
            // 遍历
            if ("sequenceFlow".equals(historicActivityInstance.getActivityType())) {
                // 添加高亮连线
                highLightedFlows.add(historicActivityInstance.getActivityId());
            } else {
                // 添加高亮节点
                highLightedActivities.add(historicActivityInstance.getActivityId());
            }
        });
        // 获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(procDefId);
        ProcessEngineConfiguration configuration = processEngine.getProcessEngineConfiguration();
        ProcessDiagramGenerator diagramGenerator = configuration.getProcessDiagramGenerator();
        InputStream inputStream = diagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivities, highLightedFlows,
                configuration.getActivityFontName(), configuration.getLabelFontName(),
                configuration.getAnnotationFontName(), configuration.getClassLoader(), 1.0, true);

        response.setContentType("image/png");
        IoUtil.copy(inputStream, response.getOutputStream());
        response.flushBuffer();

    }


}
