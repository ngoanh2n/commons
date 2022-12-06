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
public class ExtensionPropSetter implements BeforeEachCallback, AfterEachCallback, BeforeAllCallback, AfterAllCallback {
    /**
     * Callback that is invoked once <em>before</em> all tests in the current
     * container.
     *
     * @param context the current extension context; never {@code null}
     */
    @Override
    public void beforeAll(ExtensionContext context) {
        setProps(context);
    }

    /**
     * Callback that is invoked once <em>after</em> all tests in the current
     * container.
     *
     * @param context the current extension context; never {@code null}
     */
    @Override
    public void afterAll(ExtensionContext context) {
        clearProps(context);
    }

    /**
     * Callback that is invoked <em>before</em> an individual test and any
     * user-defined setup methods for that test have been executed.
     *
     * @param context the current extension context; never {@code null}
     */
    @Override
    public void beforeEach(ExtensionContext context) {
        setProps(context);
    }

    /**
     * Callback that is invoked <em>after</em> an individual test and any
     * user-defined teardown methods for that test have been executed.
     *
     * @param context the current extension context; never {@code null}
     */
    @Override
    public void afterEach(ExtensionContext context) {
        clearProps(context);
    }

    private void setProps(ExtensionContext context) {
        List<PropSetter> setters = getPropSetters(context);
        setters.forEach(setter -> Prop.string(setter.name()).setValue(setter.value()));
    }

    private void clearProps(ExtensionContext context) {
        List<PropSetter> setters = getPropSetters(context);
        setters.forEach(setter -> Prop.string(setter.name()).clearValue());
    }

    private List<PropSetter> getPropSetters(ExtensionContext context) {
        return AnnotationUtils.findRepeatableAnnotations(context.getElement(), PropSetter.class);
    }
}
