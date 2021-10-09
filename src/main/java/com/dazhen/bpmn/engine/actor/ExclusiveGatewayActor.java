package com.dazhen.bpmn.engine.actor;

import com.dazhen.bpmn.engine.BpmnEngine;
import com.dazhen.bpmn.engine.FlowContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.ExclusiveGateway;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.FlowNode;
import org.flowable.bpmn.model.SequenceFlow;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author junke
 */
@Slf4j
@Component
public class ExclusiveGatewayActor implements NodeActor<ExclusiveGateway> {
    @Resource
    private BpmnEngine bpmnEngine;

    @Override
    public void act(ExclusiveGateway node, FlowContext context) {
        List<SequenceFlow> outgoingFlows = node.getOutgoingFlows();
        if (!CollectionUtils.isEmpty(outgoingFlows)) {
            SequenceFlow flow = null;
            for (SequenceFlow outgoingFlow : outgoingFlows) {
                String condition = outgoingFlow.getConditionExpression();
                if (context.matchCondition(condition) || StringUtils.isBlank(condition)) {
                    flow = outgoingFlow;
                    break;
                }
            }
            if (null == flow) {
                log.error("!!! no condition matched. it is a problem");
                return;
            }
            FlowElement targetFlowElement = flow.getTargetFlowElement();
            bpmnEngine.act((FlowNode) targetFlowElement, context);
        }
    }
}
