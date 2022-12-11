package com.github.ngoanh2n.junit5;

import com.github.ngoanh2n.Prop;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.util.AnnotationUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 * @version 1.0.0
 * @since 2021-04-10
 */
class ExtensionPropChecks implements ExecutionCondition, BeforeEachCallback, AfterEachCallback, BeforeAllCallback, AfterAllCallback {
    /**
     * {@code true}:  Allow setting multiple value for a System Property<br>
     * E.g: -Dngoanh2n=[value1,value2,value3]
     */
    public static final Prop<Boolean> multiValueEnabled = Prop.bool("ngoanh2n.prop.multiValueEnabled", true);

    //===============================================================================//

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
        String valueSet = Prop.string(name).getValue();

        if (name == null || value == null || valueSet == null) {
            return false;
        }

        if (multiValueEnabled.getValue()) {
            boolean enabled = false;
            String regex = "^\\[(.*)]$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(valueSet.trim());

            if (matcher.matches()) {
                String[] parts = matcher.group(1).split(",");
                for (String part : parts) {
                    if (Arrays.asList(value).contains(part.trim())) {
                        enabled = true;
                        break;
                    }
                }
                return enabled;
            }
        }
        return Arrays.asList(value).contains(valueSet);
    }

    private String propsToString(List<RunOnProp> annotations) {
        StringBuilder sb = new StringBuilder();
        Iterator<RunOnProp> it = annotations.iterator();

        while (it.hasNext()) {
            RunOnProp annotation = it.next();
            String name = annotation.name().trim();
            String set = Prop.string(name).getValue().trim();
            String value = "[" + String.join(",", annotation.value()) + "]";
            sb.append(String.format("@RunOnProp(name=%s,value=%s) ← %s", name, value, set));

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

    private void setProps(List<SetProp> annotations) {
        annotations.forEach(annotation -> {
            Prop<String> prop = Prop.string(annotation.name());
            if (prop.getValue() == null) {
                prop.setValue(annotation.value());
            }
        });
    }

    private void clearProps(List<SetProp> annotations) {
        annotations.forEach(annotation -> Prop.string(annotation.name()).clearValue());
    }

    private List<SetProp> getSetProps(ExtensionContext context) {
        return AnnotationUtils.findRepeatableAnnotations(context.getElement(), SetProp.class);
    }
}
