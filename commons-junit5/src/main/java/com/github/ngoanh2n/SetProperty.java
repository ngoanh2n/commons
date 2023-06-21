package com.github.ngoanh2n;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.*;

/**
 * Set JVM system property for the annotated test class or test method.<br><br>
 *
 * <b>Test Class</b>
 * <ul>
 *     <li>JVM system property has value (not null) within test class scope</li>
 *     <li>Value of JVM system property will be found all signature annotations:
 *          {@link BeforeAll @BeforeAll}, {@link BeforeEach @BeforeEach},
 *          {@link Test @Test}, {@link AfterEach @AfterEach}, {@link AfterAll @AfterAll}
 *     </li>
 *     <li>Value of JVM system property will be deleted after {@link AfterAll @AfterAll} execution is finished</li>
 * </ul>
 * <pre>{@code
 *      import com.github.ngoanh2n.SetProperty;
 *      import org.junit.jupiter.api.*;
 *
 *      &#064;SetProperty(name = "browser", value = "safari")
 *      public class SeleniumTest {
 *        &#064;BeforeAll
 *        public static void beforeAll() {
 *          // System.getProperty("browser") -> safari
 *        }
 *
 *        &#064;BeforeEach
 *        public void beforeEach() {
 *          // System.getProperty("browser") -> safari
 *        }
 *
 *        &#064;Test
 *        public void test() {
 *          // System.getProperty("browser") -> safari
 *        }
 *
 *        &#064;AfterEach
 *        public void afterEach() {
 *          // System.getProperty("browser") -> safari
 *        }
 *
 *        &#064;AfterAll
 *        public static void afterAll() {
 *          // System.getProperty("browser") -> safari
 *        }
 *      }
 * }</pre><br>
 *
 * <b>Test Method</b>
 * <ul>
 *     <li>JVM system property has value (not null) within test method scope</li>
 *     <li>
 *         Value of JVM system property will be found signature annotations:
 *         {@link BeforeEach @BeforeEach}, {@link Test @Test}, {@link AfterEach @AfterEach}
 *     </li>
 *     <li>Value of JVM system property will be deleted after {@link AfterEach @AfterEach} execution is finished</li>
 * </ul>
 * <pre>{@code
 *      import com.github.ngoanh2n.SetProperty;
 *      import org.junit.jupiter.api.*;
 *
 *      public class SeleniumTest {
 *        &#064;BeforeAll
 *        public static void beforeAll() {
 *          // System.getProperty("browser") -> null
 *        }
 *
 *        &#064;BeforeEach
 *        public void beforeEach() {
 *          // System.getProperty("browser") -> safari
 *        }
 *
 *        &#064;Test
 *        &#064;SetProperty(name = "browser", value = "safari")
 *        public void test() {
 *          // System.getProperty("browser") -> safari
 *        }
 *
 *        &#064;AfterEach
 *        public void afterEach() {
 *          // System.getProperty("browser") -> safari
 *        }
 *
 *        &#064;AfterAll
 *        public static void afterAll() {
 *          // System.getProperty("browser") -> null
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
