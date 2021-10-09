package com.dazhen.bpmn.engine.actor;

import com.dazhen.bpmn.common.enums.InstanceStatus;
import com.dazhen.bpmn.engine.FlowContext;
import com.dazhen.bpmn.mapper.InstanceMapper;
import com.dazhen.bpmn.mapper.model.InstanceDO;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.EndEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author junke
 */
@Slf4j
@Component
public class EndEventActor implements NodeActor<EndEvent> {
    @Resource
    private InstanceMapper instanceMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void act(EndEvent node, FlowContext context) {
        updateInstanceStatus(context.getInstanceId());
        log.info("process end.");
    }


    private void updateInstanceStatus(Long instanceId) {
        InstanceDO updateDO = new InstanceDO();
        updateDO.setId(instanceId);
        updateDO.setStatus(InstanceStatus.ENDED.getStatus());
        instanceMapper.updateById(updateDO);
    }

}
