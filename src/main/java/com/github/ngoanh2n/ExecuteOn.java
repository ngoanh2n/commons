package com.github.ngoanh2n;

import org.junit.jupiter.api.extension.ExtendWith;

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
@ExtendWith(ExecuteOnCondition.class)
public @interface ExecuteOn {
    String target() default "onTarget";
    String[] values() default {};
    CombineWith and() default @CombineWith;
}
