package com.github.ngoanh2n.junit5;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Array of {@linkplain SetProp}.
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface SetProps {
    /**
     * A {@linkplain SetProp} list to set JVM system property.
     *
     * @return array of {@linkplain SetProp}.
     */
    SetProp[] value();
}
