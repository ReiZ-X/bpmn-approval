package com.dazhen.bpmn.common.enums;

/**
 * @author junke
 */
public enum TaskStatus {
    SENT(0), APPROVED(1), REJECTED(2);

    private final int status;

    TaskStatus(int status) {
        this.status = status;
    }

    public static TaskStatus of(int status) {
        for (TaskStatus value : TaskStatus.values()) {
            if (value.status == status) {
                return value;
            }
        }
        return null;
    }

    public int getStatus() {
        return status;
    }
}
