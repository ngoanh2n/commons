package com.github.ngoanh2n;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.*;

/**
 * Use to set {@link #value SetProperty.value} for the JVM system property indicated by the specified {@link #name SetProperty.name}.<br><br>
 *
 * <b>Test Class</b><br>
 * JVM system property is set in test class scope.<br>
 * Value of JVM system property will be found all signature annotations:
 * {@link BeforeAll @BeforeAll}, {@link BeforeEach @BeforeEach}, {@link Test @Test}, {@link AfterEach @AfterEach}, {@link AfterAll @AfterAll}.
 * <pre>{@code
 *      import com.github.ngoanh2n.SetProperty;
 *      import org.junit.jupiter.api.*;
 *
 *      &#064;SetProperty(name = "os", value = "windows")
 *      public class SeleniumTest {
 *        &#064;BeforeAll
 *        public static void beforeAll() {
 *          // System.getProperty("os") -> windows
 *        }
 *
 *        &#064;BeforeEach
 *        public void beforeEach() {
 *          // System.getProperty("os") -> windows
 *        }
 *
 *        &#064;Test
 *        public void test() {
 *          // System.getProperty("os") -> windows
 *        }
 *
 *        &#064;AfterEach
 *        public void afterEach() {
 *          // System.getProperty("os") -> windows
 *        }
 *
 *        &#064;AfterAll
 *        public static void afterAll() {
 *          // System.getProperty("os") -> windows
 *        }
 *      }
 * }</pre><br>
 *
 * <b>Test Method</b><br>
 * JVM system property is set in test method scope.<br>
 * Value of JVM system property will be found in signature annotations:
 * {@link BeforeEach @BeforeEach}, {@link Test @Test}, {@link AfterEach @AfterEach}.
 * <pre>{@code
 *      import com.github.ngoanh2n.SetProperty;
 *      import org.junit.jupiter.api.*;
 *
 *      public class SeleniumTest {
 *        &#064;BeforeAll
 *        public static void beforeAll() {
 *          // System.getProperty("os") -> null
 *        }
 *
 *        &#064;BeforeEach
 *        public void beforeEach() {
 *          // System.getProperty("os") -> windows
 *        }
 *
 *        &#064;Test
 *        &#064;SetProperty(name = "os", value = "windows")
 *        public void test() {
 *          // System.getProperty("os") -> windows
 *        }
 *
 *        &#064;AfterEach
 *        public void afterEach() {
 *          // System.getProperty("os") -> windows
 *        }
 *
 *        &#064;AfterAll
 *        public static void afterAll() {
 *          // System.getProperty("os") -> null
 *        }
 *      }
 * }</pre><br>
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
@Repeatable(SetProperties.class)
@ExtendWith(PropertyChecks.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface SetProperty {
    /**
     * The name of the system property.
     *
     * @return The JVM system property name.
     */
    String name();

    /**
     * The value of the JVM system property.
     *
     * @return The JVM system property value.
     */
    String value();
}
