package com.dazhen.bpmn.controller.validator;

import org.flowable.bpmn.model.FlowNode;

/**
 * @author junke
 */
public interface NodeValidator {

    /**
     * 验证bpmn中的节点
     *
     * @param flowNode 节点元素
     * @return 错误信息；没有返回null
     */
    String validate(FlowNode flowNode);
}
