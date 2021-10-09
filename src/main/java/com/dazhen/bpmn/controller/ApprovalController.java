package com.dazhen.bpmn.controller;

import com.dazhen.bpmn.common.Resp;
import com.dazhen.bpmn.controller.vo.ApprovalReqDto;
import com.dazhen.bpmn.service.ApprovalService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author junke
 */
@RestController
public class ApprovalController {
    @Resource
    private ApprovalService approvalService;

    @PostMapping("approval")
    public Resp<Boolean> approve(@RequestBody @Valid ApprovalReqDto reqDto) {
        //todo auth verify
        approvalService.approval(reqDto.getTaskId(), reqDto.getStatus());
        return Resp.ok(true);
    }
}
