package com.github.ngoanh2n.junit5;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Array of {@linkplain RunOnProp}.
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface RunOnProps {
    /**
     * A {@linkplain RunOnProp} list to annotate test class or test method is only enabled.
     *
     * @return array of {@linkplain RunOnProp}.
     */
    RunOnProp[] value();
}
