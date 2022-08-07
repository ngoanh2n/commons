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
public class ExecuteOnTargetCondition implements ExecutionCondition {

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        Optional<ExecuteOnTarget> annotationOpt = findAnnotation(context.getElement(), ExecuteOnTarget.class);

        if (!annotationOpt.isPresent()) {
            return enabled("There is no @ExecuteOnTarget");
        } else {
            String passedTarget = System.getProperty("target");
            String[] attachedTargets = annotationOpt.get().value();

            if (passedTarget == null) {
                return disabled("There is no TARGET");
            } else {
                for (String attachedTarget : attachedTargets) {
                    if (attachedTarget.equals(passedTarget)) {
                        return enabled(String.format("<%s> in %s", passedTarget, Arrays.toString(attachedTargets)));
                    }
                }
            }
            return disabled(String.format("<%s> not in %s", passedTarget, Arrays.toString(attachedTargets)));
        }
    }
}
