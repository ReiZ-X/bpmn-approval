package com.dazhen.bpmn.engine.actor;

import com.dazhen.bpmn.engine.FlowContext;
import org.flowable.bpmn.model.FlowNode;

import java.lang.reflect.ParameterizedType;

/**
 * @author junke
 */
public interface NodeActor<T extends FlowNode> {

    default Class<T> getTClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
    }

    /**
     * 不同节点的执行动作
     *
     * @param node 节点
     */
    void act(T node, FlowContext context);

    default void goOn(T node, FlowContext context) {
    }
}
