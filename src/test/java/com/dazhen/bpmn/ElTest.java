package com.dazhen.bpmn;

import de.odysseus.el.ExpressionFactoryImpl;
import de.odysseus.el.util.SimpleContext;
import org.junit.Test;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

/**
 * @author junke
 */
public class ElTest {

    @Test
    public void t1() {
        ExpressionFactory factory = new ExpressionFactoryImpl();
        SimpleContext context = new SimpleContext();
        context.setVariable("agree", factory.createValueExpression(true, boolean.class));

        ValueExpression ve = factory.createValueExpression(context, "${agree}", boolean.class);
        Object value = ve.getValue(context);

        System.out.println(value.getClass());


    }
}
