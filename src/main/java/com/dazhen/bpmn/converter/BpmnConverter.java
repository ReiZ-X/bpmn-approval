package com.dazhen.bpmn.converter;

import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @author junke
 */
public class BpmnConverter {
    private static final BpmnXMLConverter converter = new BpmnXMLConverter();

    public static BpmnModel convert2Model(InputStream is) throws XMLStreamException {
        XMLInputFactory xif = XMLInputFactory.newInstance();
        InputStreamReader in = new InputStreamReader(is, StandardCharsets.UTF_8);
        XMLStreamReader xtr = xif.createXMLStreamReader(in);
        return converter.convertToBpmnModel(xtr);
    }

    public static String convert2XmlStr(BpmnModel model) {
        return new String(converter.convertToXML(model), StandardCharsets.UTF_8);
    }

}
