package com.dazhen.bpmn.schedule;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author junke
 * 超过指定时间后任务未审批，再次给与提醒
 */
@Component
public class AgainNotifyAssigneeJob {

    @Async
    @Scheduled(cron = "0/10 * * * * ?")
    public void checkAndNotify() {
        //todo
    }
}
