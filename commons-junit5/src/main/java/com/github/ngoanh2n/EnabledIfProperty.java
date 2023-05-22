package com.github.ngoanh2n;

import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Use to signal that the annotated test class or test method is only <em>enabled</em>
 * if the value of the specified {@link #name} equals to any value in {@link #value} array.
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
@Retention(RUNTIME)
@Target({TYPE, METHOD})
@Repeatable(EnabledIfProperties.class)
@ExtendWith(PropChecks.class)
public @interface EnabledIfProperty {
    /**
     * The name of the JVM system property to retrieve.
     *
     * @return The JVM system property name.
     */
    String name();

    /**
     * A value list of JVM system property.
     *
     * @return Values of the JVM system property.
     */
    String[] value();
}
