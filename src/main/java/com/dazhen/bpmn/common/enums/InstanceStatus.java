package com.dazhen.bpmn.common.enums;

/**
 * @author junke
 */
public enum InstanceStatus {
    /**
     * 已提交
     */
    SUBMIT(0),
    /**
     * 已开始
     */
    STARTED(1),
    /**
     * 已结束
     */
    ENDED(2);

    private int status;

    InstanceStatus(int status) {
        this.status = status;
    }

    public static InstanceStatus of(int status) {
        for (InstanceStatus value : InstanceStatus.values()) {
            if (value.getStatus() == status) {
                return value;
            }
        }
        return null;
    }

    public int getStatus() {
        return status;
    }
}
