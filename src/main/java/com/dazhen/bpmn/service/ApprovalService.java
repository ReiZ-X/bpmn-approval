package com.dazhen.bpmn.service;

import com.dazhen.bpmn.common.enums.TaskStatus;
import com.dazhen.bpmn.engine.BpmnEngine;
import com.dazhen.bpmn.mapper.TaskMapper;
import com.dazhen.bpmn.mapper.model.TaskDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author junke
 */
@Slf4j
@Service
public class ApprovalService {
    @Resource
    private TaskMapper taskMapper;
    @Resource
    private BpmnEngine bpmnEngine;

    @Transactional(rollbackFor = Exception.class)
    public void approval(Long taskId, Integer status) {
        TaskDO taskDO = taskMapper.selectById(taskId);
        TaskDO updateModel = new TaskDO();
        updateModel.setId(taskId);
        updateModel.setStatus(status);
        taskMapper.updateById(updateModel);
        log.info("do approval. taskId:{}, status:{}", taskId, status);
        bpmnEngine.goOn(taskDO, TaskStatus.of(status));
    }
}
