package com.dazhen.bpmn.engine;

import de.odysseus.el.ExpressionFactoryImpl;
import de.odysseus.el.util.SimpleContext;
import lombok.NoArgsConstructor;

import javax.el.ExpressionFactory;

/**
 * @author junke
 */
@NoArgsConstructor
public class FlowContext {
    private static final String FIELD_AGREE = "agree";
    private static final String FIELD_INSTANCE_ID = "instanceId";
    private static final String EXTRACT_FORMAT = "${%s}";
    private final SimpleContext context = new SimpleContext();
    private final ExpressionFactory factory = new ExpressionFactoryImpl();

    public FlowContext(Long instanceId) {
        setInstanceId(instanceId);
    }

    public boolean matchCondition(String condition) {
        return (boolean) factory.createValueExpression(context, condition, boolean.class).getValue(context);
    }

    public Boolean getAgree() {
        return (Boolean) factory.createValueExpression(context, String.format(EXTRACT_FORMAT, FIELD_AGREE), Boolean.class).getValue(context);

    }

    public void setAgree(boolean agree) {
        context.setVariable(FIELD_AGREE, factory.createValueExpression(agree, boolean.class));
    }

    public Long getInstanceId() {
        return (Long) factory.createValueExpression(context, String.format(EXTRACT_FORMAT, FIELD_INSTANCE_ID), Long.class).getValue(context);
    }

    public void setInstanceId(Long instanceId) {
        context.setVariable(FIELD_INSTANCE_ID, factory.createValueExpression(instanceId, Long.class));
    }
}
