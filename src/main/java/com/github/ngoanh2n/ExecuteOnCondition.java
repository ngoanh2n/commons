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
        Optional<ExecuteOn> executeOpt = findAnnotation(context.getElement(), ExecuteOn.class);

        if (!executeOpt.isPresent()) {
            return enabled("There is no @ExecuteOn");
        } else {
            ExecuteOn execute = executeOpt.get();
            String[] onTargets = execute.values();
            String onTarget = Prop.string(execute.target()).getValue();

            if (onTarget == null) {
                return disabled("There is no values for @ExecuteOn");
            } else {
                if (Arrays.asList(onTargets).contains(onTarget)) {
                    CombineWith combine = execute.and();

                    if (combine != null) {
                        String[] withTargets = combine.values();
                        String withTarget = Prop.string(combine.target()).getValue();

                        if (withTarget == null) {
                            return disabled("There is no values for @CombineWith");
                        } else {
                            if (Arrays.asList(withTargets).contains(withTarget)) {
                                String reason = String.format("<%s> in %s", withTarget, Arrays.toString(withTargets));
                                return enabled(reason);
                            }
                            String reason = String.format("<%s> not in %s", withTarget, Arrays.toString(withTargets));
                            return disabled(reason);
                        }
                    }
                    String reason = String.format("<%s> in %s", onTarget, Arrays.toString(onTargets));
                    return enabled(reason);
                }
            }

            String reason = String.format("<%s> not in %s", onTarget, Arrays.toString(onTargets));
            return disabled(reason);
        }
    }
}
