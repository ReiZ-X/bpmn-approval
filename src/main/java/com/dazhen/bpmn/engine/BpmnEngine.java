package com.dazhen.bpmn.engine;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dazhen.bpmn.common.enums.TaskStatus;
import com.dazhen.bpmn.converter.BpmnConverter;
import com.dazhen.bpmn.engine.actor.NodeActor;
import com.dazhen.bpmn.mapper.InstanceMapper;
import com.dazhen.bpmn.mapper.ProcessHisMapper;
import com.dazhen.bpmn.mapper.ProcessMapper;
import com.dazhen.bpmn.mapper.model.InstanceDO;
import com.dazhen.bpmn.mapper.model.ProcessDO;
import com.dazhen.bpmn.mapper.model.ProcessHisDO;
import com.dazhen.bpmn.mapper.model.TaskDO;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.*;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.xml.stream.XMLStreamException;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author junke
 */
@Slf4j
@Component
public class BpmnEngine implements ApplicationContextAware {
    public final Map<Class<FlowNode>, NodeActor> map = new HashMap<>(16);
    private ExecutorService executorService = new ThreadPoolExecutor(
            5,
            5,
            60 * 1000, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(20), r -> new Thread(r, "BpmnEngine-Instance-Starter"));
    @Resource
    private InstanceMapper instanceMapper;
    @Resource
    private ProcessMapper processMapper;
    @Resource
    private ProcessHisMapper processHisMapper;

    @Transactional(rollbackFor = Exception.class)
    public Boolean submitInstance(Integer processId) {
        ProcessDO processDO = processMapper.selectById(processId);
        InstanceDO instanceDO = new InstanceDO();
        instanceDO.setProcessId(processDO.getId());
        instanceDO.setProcessVersion(processDO.getVersion());
        instanceMapper.insert(instanceDO);
        executorService.execute(() -> BpmnEngine.this.start(instanceDO.getId()));
        return true;
    }

    public void start(Long instanceId) {
        BpmnModel model = getBpmn(instanceId);
        if (null == model) {
            log.error("no bpmn model found with instance id:{}", instanceId);
            return;
        }
        Process process = model.getProcesses().get(0);
        StartEvent startEvent = findStart(process);
        if (null == startEvent) {
            log.error("No start element found. instance id:{}", instanceId);
            return;
        }
        FlowContext context = new FlowContext();
        context.setInstanceId(instanceId);
        act(startEvent, context);
    }

    public void act(FlowNode node, FlowContext context) {
        NodeActor nodeActor = map.get(node.getClass());
        if (null == nodeActor) {
            log.error("!!!no node actor for node:{}", node.getClass());
            return;
        }
        nodeActor.act(node, context);
    }

    public void goOn(TaskDO task, TaskStatus status) {
        Long instanceId = task.getInstanceId();
        BpmnModel model = getBpmn(instanceId);
        if (null == model) {
            log.error("no bpmn model found with instance id:{}", instanceId);
            return;
        }
        FlowElement element = findElement(model, task.getUserTaskId());
        FlowContext flowContext = new FlowContext();
        flowContext.setAgree(status == TaskStatus.APPROVED);
        flowContext.setInstanceId(task.getInstanceId());
        NodeActor nodeActor = map.get(element.getClass());
        if (null == nodeActor) {
            log.error("!!!no element actor for node:{}", element.getClass());
            return;
        }
        nodeActor.goOn((FlowNode) element, flowContext);
    }

    private FlowElement findElement(BpmnModel model, String userTaskId) {
        return model.getMainProcess().getFlowElement(userTaskId);
    }

    private BpmnModel getBpmn(Long instanceId) {
        InstanceDO instanceDO = instanceMapper.selectById(instanceId);
        ProcessDO processDO = processMapper.selectOne(new LambdaQueryWrapper<ProcessDO>()
                .eq(ProcessDO::getId, instanceDO.getProcessId())
                .eq(ProcessDO::getVersion, instanceDO.getProcessVersion())
        );
        String bpmnXml;
        if (null == processDO) {
            ProcessHisDO hisDO = processHisMapper.selectOne(new LambdaQueryWrapper<ProcessHisDO>()
                    .eq(ProcessHisDO::getProcessId, instanceDO.getProcessId())
                    .eq(ProcessHisDO::getVersion, instanceDO.getProcessVersion())
            );
            bpmnXml = hisDO.getBpmnXml();
        } else {
            bpmnXml = processDO.getBpmnXml();
        }
        try {
            return BpmnConverter.convert2Model(new ByteArrayInputStream(bpmnXml.getBytes(StandardCharsets.UTF_8)));
        } catch (XMLStreamException e) {
            log.error("parse xml str to model failed. {}", bpmnXml);
        }
        return null;
    }


    private StartEvent findStart(Process process) {
        Collection<FlowElement> flowElements = process.getFlowElements();
        for (FlowElement element : flowElements) {
            if (element instanceof StartEvent) {
                return (StartEvent) element;
            }
        }
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        Map<String, NodeActor> actorMap = ctx.getBeansOfType(NodeActor.class);
        for (NodeActor actor : actorMap.values()) {
            Class clz = actor.getTClass();
            map.put(clz, actor);
        }
    }
}
