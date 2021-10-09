/*
package com.dazhen.bpmn.engine.actor;

import com.dazhen.bpmn.engine.FlowContext;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.ServiceTask;
import org.springframework.stereotype.Component;

*/
/**
 * @author junke
 *//*

@Slf4j
@Component
public class ServiceTaskActor implements NodeActor<ServiceTask> {
    @Override
    public void act(ServiceTask node, FlowContext context) {
        String implementation = node.getImplementation();
        String[] split = implementation.split("#");
        if (split.length != 2) {
            log.error("!!!not illegal clz_method pattern. node:{}", node);
            return;
        }
        String clz = split[0];
        String method = split[1];


    }
}
*/
