package com.github.ngoanh2n.junit5;

import com.github.ngoanh2n.Prop;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.util.AnnotationUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.junit.platform.commons.util.AnnotationUtils.findRepeatableAnnotations;

/**
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 * @version 1.0.0
 * @since 2021-04-10
 */
class ExtensionPropChecks implements ExecutionCondition, BeforeEachCallback, AfterEachCallback, BeforeAllCallback, AfterAllCallback {
    /**
     * {@inheritDoc}
     */
    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        setProps(context);
        List<RunOnProp> targets = findRepeatableAnnotations(context.getElement(), RunOnProp.class);

        if (targets.size() > 0) {
            for (RunOnProp target : targets) {
                if (!targetEnabled(target)) {
                    String reason = targetsToString(targets);
                    return ConditionEvaluationResult.disabled(reason);
                }
            }
            return ConditionEvaluationResult.enabled(targetsToString(targets));
        }
        return ConditionEvaluationResult.enabled("Not related to @ExecuteOnTarget");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void beforeAll(ExtensionContext context) {
        setProps(context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void beforeEach(ExtensionContext context) {
        setProps(context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterEach(ExtensionContext context) {
        clearProps(context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterAll(ExtensionContext context) {
        clearProps(context);
    }

    private boolean targetEnabled(RunOnProp target) {
        String name = target.name();
        String[] value = target.value();

        if (name == null || value == null) {
            return false;
        }

        Prop<String> prop = Prop.string(name);
        String propValue = prop.getValue();

        if (propValue == null) {
            return false;
        }
        return Arrays.asList(value).contains(propValue);
    }

    private String targetsToString(List<RunOnProp> targets) {
        StringBuilder sb = new StringBuilder();
        Iterator<RunOnProp> it = targets.iterator();

        while (it.hasNext()) {
            RunOnProp target = it.next();
            String name = target.name();
            String passed = Prop.string(name).getValue();
            String value = Arrays.toString(target.value());

            String toAppend = "@ExecuteOnProp(name=%s,passed=%s,value=%s)";
            sb.append(String.format(toAppend, name, passed, value));

            if (it.hasNext()) {
                sb.append("\r\n");
            }
        }
        return sb.toString();
    }

    public static void setProps(ExtensionContext context) {
        List<SetProp> setters = getPropSetters(context);
        setters.forEach(setter -> {
            Prop<String> prop = Prop.string(setter.name());
            if (prop.getValue() == null) {
                prop.setValue(setter.value());
            }
        });
    }

    private static void clearProps(ExtensionContext context) {
        List<SetProp> setters = getPropSetters(context);
        setters.forEach(setter -> Prop.string(setter.name()).clearValue());
    }

    private static List<SetProp> getPropSetters(ExtensionContext context) {
        return AnnotationUtils.findRepeatableAnnotations(context.getElement(), SetProp.class);
    }

    private static String getTestName(ExtensionContext context) {
        Optional<Class<?>> testClazz = context.getTestClass();
        return testClazz.map(Class::getSimpleName).orElse("");
    }
}
