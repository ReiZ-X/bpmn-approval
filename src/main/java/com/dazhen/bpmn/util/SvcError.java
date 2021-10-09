package com.dazhen.bpmn.util;

/**
 * @author junke
 */
public class SvcError {
    public static void error(String msg) {
        throw new ServiceException(msg);
    }
}
