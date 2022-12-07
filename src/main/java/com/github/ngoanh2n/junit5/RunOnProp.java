package com.github.ngoanh2n.junit5;

import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 * @version 1.0.0
 * @since 2021-04-10
 */
@Retention(RUNTIME)
@Target({TYPE, METHOD})
@Repeatable(RunOnProps.class)
@ExtendWith(ExtensionRunOnProp.class)
public @interface RunOnProp {
    String name() default "";

    String[] value() default {};
}
