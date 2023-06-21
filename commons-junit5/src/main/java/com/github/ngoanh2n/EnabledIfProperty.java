package com.github.ngoanh2n;

import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.*;

/**
 * Signal that the annotated test class or test method is enabled.<br>
 * <ul>
 *     <li>
 *         If there are multiple {@link EnabledIfProperty @EnabledIfProperty},
 *         then it will become {@code AND} condition to enable that test
 *     </li>
 *     <li>
 *         If {@link EnabledIfProperty @EnabledIfProperty} has multiple values,
 *         that {@link EnabledIfProperty @EnabledIfProperty} will be satisfied
 *         when value of JVM system property matched one of {@link EnabledIfProperty @EnabledIfProperty} value
 *     </li>
 * </ul>
 *
 * <b>Test Method</b>
 * <pre>{@code
 *      import com.github.ngoanh2n.EnabledIfProperty;
 *      import org.junit.jupiter.api.Test;
 *
 *      public class SeleniumTest {
 *        &#064;Test
 *        &#064;EnabledIfProperty(name = "os", value = {"macos", "windows"})
 *        &#064;EnabledIfProperty(name = "browser", value = "opera")
 *        public void operaTest() {}
 *
 *        &#064;Test
 *        &#064;EnabledIfProperty(name = "os", value = {"macos", "windows", "linux"})
 *        &#064;EnabledIfProperty(name = "browser", value = "chrome")
 *        public void chromeTest() {}
 *      }
 * }</pre>
 *
 * <ul>
 *     <li>{@code ./gradlew test --tests SeleniumTest -Dos=macos -Dbrowser=opera}<br>
 *          Tests will be enabled: {@code SeleniumTest.operaTest()}
 *     </li>
 *     <li>{@code ./gradlew test --tests SeleniumTest -Dos=windows -Dbrowser=[chrome,opera]}<br>
 *          Tests will be enabled: {@code SeleniumTest.operaTest()} and {@code SeleniumTest.chromeTest()}
 *     </li>
 * </ul>
 *
 * <b>System Property</b>
 * <ul>
 *     <li>{@code ngoanh2n.junit5.multiValueEnabled}<br>
 *         Indicate to allow setting multiple value for a JVM system property. Default to {@code true}.<br>
 *         Example: {@code -Dmykey=[value1,value2]} means {@code mykey} has 2 values: {@code value1} and {@code value2}.
 *         <ul>
 *             <li>{@code true}: Extract values from the value of JVM system property</li>
 *             <li>{@code false}: Use the value of JVM system property directly</li>
 *         </ul>
 *     </li>
 * </ul>
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
@ExtendWith(PropertyChecks.class)
@Repeatable(EnabledIfProperties.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface EnabledIfProperty {
    /**
     * The name of the JVM system property to retrieve.
     *
     * @return The JVM system property name.
     */
    String name();

    /**
     * A value list of JVM system property.
     *
     * @return Values of the JVM system property.
     */
    String[] value();
}
