package com.github.ngoanh2n;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Array of {@link EnabledIfProperty @EnabledIfProperty}.<br><br>
 *
 * <em>Repository:</em>
 * <ul>
 *     <li><em>GitHub: <a href="https://github.com/ngoanh2n/commons">ngoanh2n/commons</a></em></li>
 *     <li><em>Maven: <a href="https://mvnrepository.com/artifact/com.github.ngoanh2n/commons-junit5">com.github.ngoanh2n:commons-junit5</a></em></li>
 * </ul>
 *
 * @author ngoanh2n
 * @since 2019
 */
@Inherited
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface EnabledIfProperties {
    /**
     * {@link EnabledIfProperty @EnabledIfProperty} array to annotate test class or test method is only enabled.
     *
     * @return {@link EnabledIfProperty @EnabledIfProperty} array.
     */
    EnabledIfProperty[] value();
}
