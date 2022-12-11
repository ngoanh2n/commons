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
@Repeatable(SetProps.class)
@ExtendWith(PropChecks.class)
public @interface SetProp {
    String name();

    String value();

    /*
     * Lifecycle Callbacks: https://www.baeldung.com/junit-5-extensions
     * 01. BeforeAllCallback
     * 02. BeforeAll
     * 03. BeforeEachCallback
     * 04. BeforeEach
     * 05. BeforeTestExecutionCallback
     * 06. Test
     * 07. AfterTestExecutionCallback
     * 08. AfterEach
     * 09. AfterEachCallback
     * 10. AfterAll
     * 11. AfterAllCallback
     * */
}

