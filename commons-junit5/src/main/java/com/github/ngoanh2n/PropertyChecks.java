package com.github.ngoanh2n;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.util.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * {@link Extension} for {@link EnabledIfProperty @EnabledIfProperty} and {@link SetProperty @SetProperty}.
 *
 * @author ngoanh2n
 */
public class PropertyChecks implements ExecutionCondition, BeforeAllCallback, BeforeEachCallback, AfterEachCallback, AfterAllCallback {
    /**
     * Indicate to allow setting multiple value for a JVM System Property.
     * Default to {@code true}.<br>
     * E.g: -Dngoanh2n=[value1,value2,value3]
     */
    public static final Property<Boolean> multiValueEnabled = Property.ofBoolean("ngoanh2n.propMultiValueEnabled", true);

    private static final List<Property<String>> MULTI_VALUE_PROPERTIES = new ArrayList<>();

    /**
     * Default constructor.
     */
    public PropertyChecks() { /* No implementation necessary */ }

    //-------------------------------------------------------------------------------//

    /**
     * {@inheritDoc}
     */
    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        resetMultiValueProp();
        setProperties(getSetProperties(context));
        List<EnabledIfProperty> properties = getEnabledIfProperties(context);

        if (properties.size() > 0) {
            for (EnabledIfProperty property : properties) {
                if (!propertyEnabled(property)) {
                    String reason = propertiesToString(properties, context);
                    return ConditionEvaluationResult.disabled(reason);
                }
            }
            return ConditionEvaluationResult.enabled(propertiesToString(properties, context));
        }
        return ConditionEvaluationResult.enabled("Not related to @EnabledIfProperty");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void beforeAll(ExtensionContext context) {
        List<SetProperty> annotations = getSetProperties(context);
        setProperties(annotations);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void beforeEach(ExtensionContext context) {
        List<SetProperty> annotations = getSetProperties(context);
        setProperties(annotations);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterEach(ExtensionContext context) {
        List<SetProperty> annotations = getSetProperties(context);
        clearProperties(annotations);
        resetMultiValueProp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterAll(ExtensionContext context) {
        List<SetProperty> annotations = getSetProperties(context);
        clearProperties(annotations);
        resetMultiValueProp();
    }

    //-------------------------------------------------------------------------------//

    private boolean propertyEnabled(EnabledIfProperty annotation) {
        String name = annotation.name();
        String[] value = annotation.value();
        Property<String> property = Property.ofString(name);
        String valueSet = StringUtils.trim(property.getValue());

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

    private String propertiesToString(List<EnabledIfProperty> annotations, ExtensionContext context) {
        StringBuilder sb = new StringBuilder();
        Iterator<EnabledIfProperty> it = annotations.iterator();

        while (it.hasNext()) {
            EnabledIfProperty annotation = it.next();
            String name = StringUtils.trim(annotation.name());
            String set = StringUtils.trim(Property.ofString(name).getValue());
            String value = ("[" + String.join(",", annotation.value()) + "]").replace(" ", "");
            sb.append(String.format("@EnabledIfProperty(name=%s,value=%s) <- %s", name, value, set));

            if (it.hasNext()) {
                sb.append("\n");
            } else {
                Optional<Method> method = context.getTestMethod();
                method.ifPresent(v -> sb.append("\n").append(v));
            }
        }
        return sb.toString();
    }

    private List<EnabledIfProperty> getEnabledIfProperties(ExtensionContext context) {
        return AnnotationUtils.findRepeatableAnnotations(context.getElement(), EnabledIfProperty.class);
    }

    private void setProperties(List<SetProperty> annotations) {
        annotations.forEach(annotation -> {
            Property<String> property = Property.ofString(annotation.name());
            if (property.getValue() == null) {
                property.setValue(annotation.value());
            }
        });
    }

    private void clearProperties(List<SetProperty> annotations) {
        annotations.forEach(annotation -> Property.ofString(annotation.name()).clearValue());
    }

    private List<SetProperty> getSetProperties(ExtensionContext context) {
        return AnnotationUtils.findRepeatableAnnotations(context.getElement(), SetProperty.class);
    }

    private void resetMultiValueProp() {
        for (Property<String> multiValueProperty : MULTI_VALUE_PROPERTIES) {
            String name = multiValueProperty.getDefaultValue();
            String value = multiValueProperty.getValue();

            if (value != null) {
                Property.ofString(name).setValue(value);
            }
        }
    }

    private void resolveMultiValueProp(String name, String valuePart) {
        Property<String> property = Property.ofString(name);
        String valueSet = StringUtils.trim(property.getValue());
        property.setValue(valuePart);

        Property<String> multiValueProperty = Property.ofString(name + ".original", name);
        multiValueProperty.setValue(valueSet);
        MULTI_VALUE_PROPERTIES.add(multiValueProperty);
    }
}
