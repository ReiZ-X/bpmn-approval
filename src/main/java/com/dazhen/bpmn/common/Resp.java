package com.dazhen.bpmn.common;

import lombok.Data;

/**
 * @author junke
 */
@Data
public class Resp<T> {
    private Integer code = 200;
    private String error = null;
    private T data;

    public static <T> Resp<T> err(String err) {
        Resp<T> resp = new Resp<>();
        resp.setCode(-200);
        resp.setError(err);
        return resp;
    }

    public static <T> Resp<T> ok(T data) {
        Resp<T> resp = new Resp<>();
        resp.setData(data);
        return resp;
    }

}
