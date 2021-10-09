package com.dazhen.bpmn.engine.actor;

import com.alibaba.druid.support.json.JSONUtils;
import com.dazhen.bpmn.common.enums.TaskStatus;
import com.dazhen.bpmn.mapper.TaskMapper;
import com.dazhen.bpmn.mapper.model.TaskDO;
import org.flowable.bpmn.model.UserTask;

import javax.annotation.Resource;

/**
 * @author junke
 */
public class AbstractNodeActor {
    @Resource
    private TaskMapper taskMapper;

    public void saveTask(Long instanceId, UserTask task) {
        TaskDO model = new TaskDO();
        model.setStatus(TaskStatus.SENT.getStatus());
        model.setAssignee(task.getAssignee());
        model.setUserTaskId(task.getId());
        model.setInstanceId(instanceId);
        model.setCandidates(JSONUtils.toJSONString(task.getCandidateUsers()));
        taskMapper.insert(model);
    }

    public void updateTask(UserTask task, TaskStatus status) {
        //todo
    }
}
