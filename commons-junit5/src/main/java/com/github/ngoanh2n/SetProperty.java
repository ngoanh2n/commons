package com.github.ngoanh2n;

import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Use to set {@link #value} for the JVM system property indicated by the specified {@link #name}.
 *
 * @author ngoanh2n
 */
@Retention(RUNTIME)
@Target({TYPE, METHOD})
@Repeatable(SetProperties.class)
@ExtendWith(PropertyChecks.class)
public @interface SetProperty {
    /**
     * The name of the system property.
     *
     * @return The JVM system property name.
     */
    String name();

    /**
     * The value of the JVM system property.
     *
     * @return The JVM system property value.
     */
    String value();
}
