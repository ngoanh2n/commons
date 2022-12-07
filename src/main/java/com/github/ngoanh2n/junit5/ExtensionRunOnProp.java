package com.github.ngoanh2n.junit5;

import com.github.ngoanh2n.Prop;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.platform.commons.util.AnnotationUtils.findRepeatableAnnotations;

/**
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 * @version 1.0.0
 * @since 2021-04-10
 */
public class ExtensionRunOnProp implements ExecutionCondition {
    /**
     * {@inheritDoc}
     */
    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        ExtensionSetProp.setProps(context);
        List<RunOnProp> targets = findRepeatableAnnotations(context.getElement(), RunOnProp.class);

        if (targets.size() > 0) {
            for (RunOnProp target : targets) {
                if (!matches(target)) {
                    return ConditionEvaluationResult.disabled(toString(targets));
                }
            }
            return ConditionEvaluationResult.enabled(toString(targets));
        } else {
            return ConditionEvaluationResult.enabled("Not related to @ExecuteOnTarget");
        }
    }

    private boolean matches(RunOnProp target) {
        String name = target.name();
        String[] value = target.value();

        if (name.isEmpty()) {
            return false;
        }

        Prop<String> prop = Prop.string(name);
        String propValue = prop.getValue();

        if (propValue == null) {
            return false;
        }
        return Arrays.asList(value).contains(propValue);
    }

    private String toString(List<RunOnProp> targets) {
        StringBuilder sb = new StringBuilder();
        Iterator<RunOnProp> it = targets.iterator();

        if (it.hasNext()) {
            RunOnProp target = it.next();
            String name = target.name();
            String passed = Prop.string(name).getValue();
            String value = Arrays.toString(target.value());

            String toAppend = "@ExecuteOnProp(name=%s,passed=%s,value=%s)";
            sb.append(String.format(toAppend, name, passed, value));

            if (it.hasNext()) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
}