package com.github.ngoanh2n;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Provides a file or resource for Model that extends {@linkplain YamlData} class.
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface YamlFrom {
    /**
     * The path name of Yaml file.
     *
     * @return the path name.
     */
    String file() default "";

    /**
     * The resource name to get the Yaml file.
     *
     * @return the resource name.
     */
    String resource() default "";
}
