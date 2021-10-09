package com.dazhen.bpmn.engine.actor;

import com.dazhen.bpmn.common.enums.InstanceStatus;
import com.dazhen.bpmn.engine.BpmnEngine;
import com.dazhen.bpmn.engine.FlowContext;
import com.dazhen.bpmn.mapper.InstanceMapper;
import com.dazhen.bpmn.mapper.model.InstanceDO;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.FlowNode;
import org.flowable.bpmn.model.SequenceFlow;
import org.flowable.bpmn.model.StartEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author junke
 */
@Slf4j
@Component
public class StartEventActor implements NodeActor<StartEvent> {
    @Resource
    private BpmnEngine engine;
    @Resource
    private InstanceMapper instanceMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void act(StartEvent node, FlowContext context) {
        List<SequenceFlow> outgoingFlows = node.getOutgoingFlows();
        if (!CollectionUtils.isEmpty(outgoingFlows)) {
            for (SequenceFlow outgoingFlow : outgoingFlows) {
                engine.act((FlowNode) outgoingFlow.getTargetFlowElement(), context);
                updateInstanceStatus(context.getInstanceId());
            }
        }
    }

    private void updateInstanceStatus(Long instanceId) {
        InstanceDO updateDO = new InstanceDO();
        updateDO.setId(instanceId);
        updateDO.setStatus(InstanceStatus.STARTED.getStatus());
        instanceMapper.updateById(updateDO);
    }
}
