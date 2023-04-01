package com.github.ngoanh2n.junit5;

import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Uses to set {@linkplain #value} for the system property indicated by the specified {@linkplain #name}.
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
@Retention(RUNTIME)
@Target({TYPE, METHOD})
@Repeatable(SetProps.class)
@ExtendWith(PropChecks.class)
public @interface SetProp {
    /**
     * The name of the system property.
     *
     * @return the JVM system property name.
     */
    String name();

    /**
     * The value of the JVM system property.
     *
     * @return the JVM system property value.
     */
    String value();
}
