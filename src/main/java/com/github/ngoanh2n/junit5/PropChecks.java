package com.github.ngoanh2n.junit5;

import com.github.ngoanh2n.Prop;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.util.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * {@link Extension} for {@link RunOnProp @RunOnProp} and {@link SetProp @SetProp}.
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
public class PropChecks implements ExecutionCondition, BeforeAllCallback, BeforeEachCallback, AfterEachCallback, AfterAllCallback {
    /**
     * {@code true}:  Allow setting multiple value for a JVM System Property.<br>
     * E.g: -Dngoanh2n=[value1,value2,value3]
     */
    public static final Prop<Boolean> multiValueEnabled = Prop.bool("ngoanh2n.propMultiValueEnabled", true);

    private static final List<Prop<String>> multiValueProps = new ArrayList<>();

    /**
     * Default constructor.
     */
    public PropChecks() { /* No implementation necessary */ }

    //-------------------------------------------------------------------------------//

    /**
     * {@inheritDoc}
     */
    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        resetMultiValueProp();
        setProps(getSetProps(context));
        List<RunOnProp> props = getRunOnProps(context);

        if (props.size() > 0) {
            for (RunOnProp prop : props) {
                if (!propEnabled(prop)) {
                    String reason = propsToString(props, context);
                    return ConditionEvaluationResult.disabled(reason);
                }
            }
            return ConditionEvaluationResult.enabled(propsToString(props, context));
        }
        return ConditionEvaluationResult.enabled("Not related to @RunOnProp");
    }

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
        resetMultiValueProp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterAll(ExtensionContext context) {
        List<SetProp> annotations = getSetProps(context);
        clearProps(annotations);
        resetMultiValueProp();
    }

    //-------------------------------------------------------------------------------//

    private boolean propEnabled(RunOnProp annotation) {
        String name = annotation.name();
        String[] value = annotation.value();
        Prop<String> prop = Prop.string(name);
        String valueSet = StringUtils.trim(prop.getValue());

        if (name == null || value == null || valueSet == null) {
            return false;
        }

        if (multiValueEnabled.getValue()) {
            String regex = "^\\[(.*)]$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(valueSet);

            if (matcher.matches()) {
                for (String part : matcher.group(1).split(",")) {
                    String valuePart = part.trim();

                    if (Arrays.asList(value).contains(valuePart)) {
                        resolveMultiValueProp(name, valuePart);
                        return true;
                    }
                }
            }
        }
        return Arrays.asList(value).contains(valueSet);
    }

    private String propsToString(List<RunOnProp> annotations, ExtensionContext context) {
        StringBuilder sb = new StringBuilder();
        Iterator<RunOnProp> it = annotations.iterator();

        while (it.hasNext()) {
            RunOnProp annotation = it.next();
            String name = StringUtils.trim(annotation.name());
            String set = StringUtils.trim(Prop.string(name).getValue());
            String value = ("[" + String.join(",", annotation.value()) + "]").replace(" ", "");
            sb.append(String.format("@RunOnProp(name=%s,value=%s) <- %s", name, value, set));

            if (it.hasNext()) {
                sb.append("\n");
            } else {
                Optional<Method> method = context.getTestMethod();
                method.ifPresent(v -> sb.append("\n").append(v));
            }
        }
        return sb.toString();
    }

    private List<RunOnProp> getRunOnProps(ExtensionContext context) {
        return AnnotationUtils.findRepeatableAnnotations(context.getElement(), RunOnProp.class);
    }

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

    private void resetMultiValueProp() {
        for (Prop<String> multiValueProp : multiValueProps) {
            String name = multiValueProp.getDefaultValue();
            String value = multiValueProp.getValue();

            if (value != null) {
                Prop.string(name).setValue(value);
            }
        }
    }

    private void resolveMultiValueProp(String name, String valuePart) {
        Prop<String> prop = Prop.string(name);
        String valueSet = StringUtils.trim(prop.getValue());
        prop.setValue(valuePart);

        Prop<String> multiValueProp = Prop.string(name + ".original", name);
        multiValueProp.setValue(valueSet);
        multiValueProps.add(multiValueProp);
    }
}
