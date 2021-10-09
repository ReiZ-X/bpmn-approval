package com.dazhen.bpmn.controller;

import com.dazhen.bpmn.common.Resp;
import com.dazhen.bpmn.controller.validator.BpmnValidator;
import com.dazhen.bpmn.controller.vo.BpmnSaveReqDto;
import com.dazhen.bpmn.controller.vo.SubmitInstanceReqDto;
import com.dazhen.bpmn.service.BpmnService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Optional;

/**
 * @author junke
 */
@RestController
@RequestMapping("bpmn")
public class BpmnController {
    @Resource
    private BpmnService bpmnService;

    @PostMapping("save")
    public Resp<Boolean> saveProcess(@RequestBody @Valid BpmnSaveReqDto reqDto) {
        Optional<String> errorNullable = Optional.ofNullable(BpmnValidator.validate(reqDto.getBpmnXml()));
        if (errorNullable.isPresent()) {
            return Resp.err(errorNullable.get());
        }
        bpmnService.saveBpmn(reqDto.getName(), reqDto.getBpmnXml());
        return Resp.ok(true);
    }


    @PostMapping("submit")
    public Resp<Boolean> submitInstance(@RequestBody @Valid SubmitInstanceReqDto reqDto) {
        bpmnService.submitInstance(reqDto.getProcessId());
        return Resp.ok(true);
    }

}
