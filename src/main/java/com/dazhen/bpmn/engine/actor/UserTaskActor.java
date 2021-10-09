package com.dazhen.bpmn.engine.actor;

import com.alibaba.druid.support.json.JSONUtils;
import com.dazhen.bpmn.engine.BpmnEngine;
import com.dazhen.bpmn.engine.FlowContext;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.FlowNode;
import org.flowable.bpmn.model.SequenceFlow;
import org.flowable.bpmn.model.UserTask;
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
public class UserTaskActor extends AbstractNodeActor implements NodeActor<UserTask> {
    @Resource
    private BpmnEngine bpmnEngine;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void act(UserTask node, FlowContext context) {
        //SENT
        String assignee = node.getAssignee();
        if (StringUtils.isNotBlank(assignee)) {
            log.info("prepare to notify {} do approval.", node.getAssignee());
            notifyAssignee(Lists.newArrayList(assignee));
        } else {
            List<String> candidateUsers = node.getCandidateUsers();
            log.info("prepare to notify candidates: {} do approval.", JSONUtils.toJSONString(candidateUsers));
            notifyAssignee(candidateUsers);
        }
        saveTask(context.getInstanceId(), node);
    }

    @Override
    public void goOn(UserTask node, FlowContext context) {
        List<SequenceFlow> outgoingFlows = node.getOutgoingFlows();
        if (CollectionUtils.isEmpty(outgoingFlows)) {
            log.warn("this user task:[{}] have no next node. ", node.getName());
            return;
        }
        SequenceFlow sequenceFlow = outgoingFlows.get(0);
        bpmnEngine.act((FlowNode) sequenceFlow.getTargetFlowElement(), context);
    }

    private void notifyAssignee(List<String> assignees) {
        //todo
    }
}
