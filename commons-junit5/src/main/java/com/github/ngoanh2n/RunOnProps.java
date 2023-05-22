package com.github.ngoanh2n;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Array of {@link RunOnProp @RunOnProp}.
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface RunOnProps {
    /**
     * {@link RunOnProp @RunOnProp} array to annotate test class or test method is only enabled.
     *
     * @return {@link RunOnProp @RunOnProp} array.
     */
    RunOnProp[] value();
}
