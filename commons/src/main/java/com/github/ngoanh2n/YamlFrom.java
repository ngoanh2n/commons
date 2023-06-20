package com.github.ngoanh2n;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Provide a file or resource for {@code Model} that extends {@link YamlData} class.<br><br>
 *
 * <em>Repository:</em>
 * <ul>
 *     <li><em>GitHub: <a href="https://github.com/ngoanh2n/commons">ngoanh2n/commons</a></em></li>
 *     <li><em>Maven: <a href="https://mvnrepository.com/artifact/com.github.ngoanh2n/commons">com.github.ngoanh2n:commons</a></em></li>
 * </ul>
 *
 * @author ngoanh2n
 * @since 2019
 * @since 2019
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface YamlFrom {
    /**
     * The path name of Yaml file.
     *
     * @return The path name.
     */
    String file() default "";

    /**
     * The resource name to get the Yaml file.
     *
     * @return The resource name.
     */
    String resource() default "";
}
