package com.github.ngoanh2n.junit5;

import com.github.ngoanh2n.Prop;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.extension.ConditionEvaluationResult.disabled;
import static org.junit.jupiter.api.extension.ConditionEvaluationResult.enabled;
import static org.junit.platform.commons.util.AnnotationUtils.findAnnotation;

/**
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 * @version 1.0.0
 * @since 2021-04-10
 */
public class ExecuteOnCondition implements ExecutionCondition {
    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        Optional<ExecuteOnTarget> executeOpt = findAnnotation(context.getElement(), ExecuteOnTarget.class);

        if (!executeOpt.isPresent()) {
            return enabled("Not related to @ExecuteOnTarget");
        } else {
            ExecuteOnTarget exe = executeOpt.get();
            String[] exeValues = exe.values();
            Prop<String> exeProp = Prop.string(exe.name());

            if (exeProp.getValue() == null) {
                return disabled(annToString(exe, exeProp));
            } else {
                if (Arrays.asList(exeValues).contains(exeProp.getValue())) {
                    WithTarget with = exe.combine();
                    String[] withValues = with.values();
                    Prop<String> withProp = Prop.string(with.name());
                    String defaultValue = with.defaultValue();

                    if (withValues.length == 0) {
                        if (defaultValue.isEmpty()) {
                            return enabled(annToString(exe, exeProp));
                        } else {
                            withProp.setValue(defaultValue);
                            return enabled(annToString(exe, exeProp, with, withProp));
                        }
                    } else {
                        if (withProp.getValue() == null) {
                            withProp.setValue(defaultValue);
                            return enabled(annToString(exe, exeProp, with, withProp));
                        } else {
                            if (Arrays.asList(withValues).contains(withProp.getValue())) {
                                return enabled(annToString(exe, exeProp, with, withProp));
                            } else {
                                return disabled(annToString(exe, exeProp, with, withProp));
                            }
                        }
                    }
                }
            }
            return disabled(annToString(exe, exeProp));
        }
    }

    private String annToString(ExecuteOnTarget e, Prop<String> ep) {
        return String.format("@ExecuteOnTarget(name=%s), passed=%s, values=%s)", e.name(), ep.getValue(), Arrays.asList(e.values()));
    }

    private String annToString(ExecuteOnTarget e, Prop<String> ep, WithTarget c, Prop<String> cp) {
        return String.format("@ExecuteOnTarget(name=%s, passed=%s, values=%s, and=@CombineWithTarget(name=%s, passed=%s, values=%s, defaultValue=%s))",
                e.name(), ep.getValue(), Arrays.asList(e.values()), c.name(), cp.getValue(), Arrays.asList(c.values()), c.defaultValue());
    }
}
