package com.dazhen.bpmn.engine.actor;

import com.dazhen.bpmn.engine.BpmnEngine;
import com.dazhen.bpmn.engine.FlowContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.FlowNode;
import org.flowable.bpmn.model.ParallelGateway;
import org.flowable.bpmn.model.SequenceFlow;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author junke
 */
@Slf4j
@Component
public class ParallelGatewayActor implements NodeActor<ParallelGateway> {
    private ExecutorService executorService = new ThreadPoolExecutor(
            5,
            5,
            60 * 1000, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(20), r -> new Thread(r, "BpmnEngine-Instance-Starter"));

    @Resource
    private BpmnEngine bpmnEngine;

    @Override
    public void act(ParallelGateway node, FlowContext context) {
        AtomicInteger index = new AtomicInteger(1);
        for (SequenceFlow outgoingFlow : node.getOutgoingFlows()) {
            String condition = outgoingFlow.getConditionExpression();
            if (context.matchCondition(condition) || StringUtils.isBlank(condition)) {
                executorService.execute(() -> {
                    FlowElement element = outgoingFlow.getTargetFlowElement();
                    log.info("parallel gateway executed. instance:{}, node:{}, branch:{}",
                            context.getInstanceId(), node.getName(), index.getAndIncrement());
                    bpmnEngine.act((FlowNode) element, context);
                });
            }
        }
    }
}
