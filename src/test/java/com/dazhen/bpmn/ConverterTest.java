package com.dazhen.bpmn;

import com.dazhen.bpmn.converter.BpmnConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.Process;
import org.junit.Test;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

/**
 * @author junke
 */
public class ConverterTest {

    @Test
    public void test01() throws IOException, XMLStreamException {
//        String s = IOUtils.toString(ConverterTest.class.getResourceAsStream("/bpmn/demo.xml"), StandardCharsets.UTF_8);
        InputStream is = ConverterTest.class.getResourceAsStream("/bpmn/demo.xml");
        BpmnModel bpmnModel = BpmnConverter.convert2Model(is);
        Process process = bpmnModel.getMainProcess();
        Collection<FlowElement> flowElements = process.getFlowElements();
        int index = 0;
        for (FlowElement flowElement : flowElements) {
            if (index++ == 15) {
                System.out.println(flowElement);
            }

        }
    }
}
