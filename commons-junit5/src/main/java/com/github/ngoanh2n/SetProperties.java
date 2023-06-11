package com.github.ngoanh2n;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Array of {@link SetProperty @SetProperty}.
 *
 * @author ngoanh2n
 */
@Inherited
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface SetProperties {
    /**
     * {@link SetProperty @SetProperty} array to set JVM system property.
     *
     * @return {@link SetProperty @SetProperty} array.
     */
    SetProperty[] value();
}
