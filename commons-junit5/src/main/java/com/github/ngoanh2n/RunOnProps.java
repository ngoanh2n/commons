package com.github.ngoanh2n;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Array of {@link EnabledIfProperty @EnabledIfProperty}.
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface RunOnProps {
    /**
     * {@link EnabledIfProperty @EnabledIfProperty} array to annotate test class or test method is only enabled.
     *
     * @return {@link EnabledIfProperty @EnabledIfProperty} array.
     */
    EnabledIfProperty[] value();
}
