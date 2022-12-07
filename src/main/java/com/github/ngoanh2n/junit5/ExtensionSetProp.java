package com.github.ngoanh2n.junit5;

import com.github.ngoanh2n.Prop;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.util.AnnotationUtils;

import java.util.List;

/**
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 * @version 1.0.0
 * @since 2021-04-10
 */
public class ExtensionSetProp implements BeforeEachCallback, AfterEachCallback, BeforeAllCallback, AfterAllCallback {
    public static void setProps(ExtensionContext context) {
        List<SetProp> setters = getPropSetters(context);
        setters.forEach(setter -> Prop.string(setter.name()).setValue(setter.value()));
    }

    private static void clearProps(ExtensionContext context) {
        List<SetProp> setters = getPropSetters(context);
        setters.forEach(setter -> Prop.string(setter.name()).clearValue());
    }

    private static List<SetProp> getPropSetters(ExtensionContext context) {
        return AnnotationUtils.findRepeatableAnnotations(context.getElement(), SetProp.class);
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
    public void afterAll(ExtensionContext context) {
        clearProps(context);
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
}
