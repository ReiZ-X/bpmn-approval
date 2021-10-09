package com.dazhen.bpmn.controller.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author junke
 */
@Data
public class SubmitInstanceReqDto {
    @NotNull
    private Integer processId;
}
