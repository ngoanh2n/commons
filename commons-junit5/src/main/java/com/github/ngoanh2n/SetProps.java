package com.github.ngoanh2n;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Array of {@link SetProp @SetProp}.
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface SetProps {
    /**
     * {@link SetProp @SetProp} array to set JVM system property.
     *
     * @return {@link SetProp @SetProp} array.
     */
    SetProp[] value();
}
