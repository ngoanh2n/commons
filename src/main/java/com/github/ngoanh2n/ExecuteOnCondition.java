package com.github.ngoanh2n;

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
                    CombineWithTarget com = exe.and();
                    String[] comValues = com.values();
                    Prop<String> comProp = Prop.string(com.name());
                    String defaultValue = com.defaultValue();

                    if (comValues.length == 0) {
                        if (defaultValue.isEmpty()) {
                            return enabled(annToString(exe, exeProp));
                        } else {
                            comProp.setValue(defaultValue);
                            return enabled(annToString(exe, exeProp, com, comProp));
                        }
                    } else {
                        if (comProp.getValue() == null) {
                            comProp.setValue(defaultValue);
                            return enabled(annToString(exe, exeProp, com, comProp));
                        } else {
                            if (Arrays.asList(comValues).contains(comProp.getValue())) {
                                return enabled(annToString(exe, exeProp, com, comProp));
                            } else {
                                return disabled(annToString(exe, exeProp, com, comProp));
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

    private String annToString(ExecuteOnTarget e, Prop<String> ep, CombineWithTarget c, Prop<String> cp) {
        return String.format("@ExecuteOnTarget(name=%s, passed=%s, values=%s, and=@CombineWithTarget(name=%s, passed=%s, values=%s, defaultValue=%s))",
                e.name(), ep.getValue(), Arrays.asList(e.values()), c.name(), cp.getValue(), Arrays.asList(c.values()), c.defaultValue());
    }
}
