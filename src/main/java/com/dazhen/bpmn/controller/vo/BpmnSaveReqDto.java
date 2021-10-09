package com.dazhen.bpmn.controller.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author junke
 */
@Data
public class BpmnSaveReqDto {

    @NotBlank
    private String bpmnXml;
    @NotBlank
    private String name;
}
