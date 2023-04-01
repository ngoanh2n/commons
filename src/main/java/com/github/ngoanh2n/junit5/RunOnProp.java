package com.github.ngoanh2n.junit5;

import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Uses to signal that the annotated test class or test method is only <em>enabled</em>
 * if the value of the specified {@linkplain #name} equals to any value in {@linkplain #value} array.
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
@Retention(RUNTIME)
@Target({TYPE, METHOD})
@Repeatable(RunOnProps.class)
@ExtendWith(PropChecks.class)
public @interface RunOnProp {
    /**
     * The name of the JVM system property to retrieve.
     *
     * @return the JVM system property name.
     */
    String name();

    /**
     * A value list of JVM system property.
     *
     * @return values of the JVM system property.
     */
    String[] value();
}
