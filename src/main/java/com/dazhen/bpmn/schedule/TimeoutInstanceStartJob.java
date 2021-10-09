package com.dazhen.bpmn.schedule;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author junke
 * 实例提交后，startEvent未能执行的，重试开始执行
 */
@Component
public class TimeoutInstanceStartJob {

    @Async
    @Scheduled(cron = "0/10 * * * * ?")
    public void checkAndStart() {
        //todo
    }
}
