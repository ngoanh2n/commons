package com.github.ngoanh2n.junit5;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 * @version 1.0.0
 * @since 2021-04-10
 */
@Retention(RUNTIME)
@Target({PARAMETER})
public @interface WithTarget {
    String name() default "withTarget";

    String[] values() default {};

    String defaultValue() default "";
}
