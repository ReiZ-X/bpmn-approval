package com.dazhen.bpmn.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dazhen.bpmn.engine.BpmnEngine;
import com.dazhen.bpmn.mapper.ProcessHisMapper;
import com.dazhen.bpmn.mapper.ProcessMapper;
import com.dazhen.bpmn.mapper.model.ProcessDO;
import com.dazhen.bpmn.mapper.model.ProcessHisDO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static com.dazhen.bpmn.util.BeanUtil.copyWithCopier;

/**
 * @author junke
 */
@Service
public class BpmnService {
    @Resource
    private ProcessMapper processMapper;
    @Resource
    private ProcessHisMapper processHisMapper;
    @Resource
    private BpmnEngine bpmnEngine;

    @Transactional(rollbackFor = Exception.class)
    public void saveBpmn(String name, String bpmnXml) {
        ProcessDO namedDO = processMapper.selectOne(new LambdaQueryWrapper<ProcessDO>()
                .eq(ProcessDO::getVersion, name)
        );
        if (null != namedDO) {
            //save to current to his table
            ProcessHisDO hisDO = new ProcessHisDO();
            copyWithCopier(namedDO, hisDO);
            hisDO.setProcessId(namedDO.getId());
            processHisMapper.insert(hisDO);
            //update current
            namedDO.setBpmnXml(bpmnXml);
            namedDO.setVersion(namedDO.getVersion() + 1);
            processMapper.updateById(namedDO);
        } else {
            ProcessDO process = new ProcessDO();
            process.setName(name);
            process.setBpmnXml(bpmnXml);
            processMapper.insert(process);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void submitInstance(Integer processId) {
        bpmnEngine.submitInstance(processId);
    }
}
