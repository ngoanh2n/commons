package com.github.ngoanh2n.junit5;

import com.github.ngoanh2n.Prop;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.util.AnnotationUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

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
        setProps(getSetProps(context));
        List<RunOnProp> props = getRunOnProps(context);

        if (props.size() > 0) {
            for (RunOnProp prop : props) {
                if (!propEnabled(prop)) {
                    String reason = propsToString(props);
                    return ConditionEvaluationResult.disabled(reason);
                }
            }
            return ConditionEvaluationResult.enabled(propsToString(props));
        }
        return ConditionEvaluationResult.enabled("Not related to @RunOnProp");
    }

    //===============================================================================//

    /**
     * {@inheritDoc}
     */
    @Override
    public void beforeAll(ExtensionContext context) {
        List<SetProp> annotations = getSetProps(context);
        setProps(annotations);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void beforeEach(ExtensionContext context) {
        List<SetProp> annotations = getSetProps(context);
        setProps(annotations);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterEach(ExtensionContext context) {
        List<SetProp> annotations = getSetProps(context);
        clearProps(annotations);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterAll(ExtensionContext context) {
        List<SetProp> annotations = getSetProps(context);
        clearProps(annotations);
    }

    //===============================================================================//

    private boolean propEnabled(RunOnProp annotation) {
        String name = annotation.name();
        String[] value = annotation.value();

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

    private String propsToString(List<RunOnProp> annotations) {
        StringBuilder sb = new StringBuilder();
        Iterator<RunOnProp> it = annotations.iterator();

        while (it.hasNext()) {
            RunOnProp annotation = it.next();
            String name = annotation.name();
            String value = Arrays.toString(annotation.value());
            String set = Prop.string(name).getValue();

            String toAppend = "@RunOnProp(set=%s,name=%s,value=%s)";
            sb.append(String.format(toAppend, set, name, value));

            if (it.hasNext()) {
                sb.append("\r\n");
            }
        }
        return sb.toString();
    }

    private List<RunOnProp> getRunOnProps(ExtensionContext context) {
        return AnnotationUtils.findRepeatableAnnotations(context.getElement(), RunOnProp.class);
    }

    //===============================================================================//

    private static void setProps(List<SetProp> annotations) {
        annotations.forEach(annotation -> {
            Prop<String> prop = Prop.string(annotation.name());
            if (prop.getValue() == null) {
                prop.setValue(annotation.value());
            }
        });
    }

    private static void clearProps(List<SetProp> annotations) {
        annotations.forEach(annotation -> Prop.string(annotation.name()).clearValue());
    }

    private static List<SetProp> getSetProps(ExtensionContext context) {
        return AnnotationUtils.findRepeatableAnnotations(context.getElement(), SetProp.class);
    }

    private static String getTestName(ExtensionContext context) {
        Optional<Class<?>> testClazz = context.getTestClass();
        return testClazz.map(Class::getSimpleName).orElse("");
    }
}
