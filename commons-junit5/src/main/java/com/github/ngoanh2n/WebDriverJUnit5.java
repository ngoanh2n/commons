package com.github.ngoanh2n;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * Lookup {@link WebDriver} from the current JUnit5 test using {@link InvocationInterceptor}.
 * <ul>
 *     <li>Step 1: Create a class that extends {@link WebDriverJUnit5}
 *          <pre>{@code
 *              package com.company.project.impl;
 *
 *              import com.github.ngoanh2n.WebDriverJUnit5;
 *
 *              public class MyWebDriverLookup extends WebDriverJUnit5 {
 *                  public WebDriver getWebDriver() {
 *                      if (invocationContext != null) {
 *                          lookupDriver(invocationContext, WebDriverJUnit5.BO);
 *                      }
 *                      return driver;
 *                  }
 *              }
 *          }</pre>
 *     </li>
 *     <li>Step 2: Create a provider configuration file
 *          <ul>
 *              <li>Location: {@code resources/META-INF/services/}</li>
 *              <li>Name: {@code org.junit.jupiter.api.extension.Extension}</li>
 *              <li>Content: {@code com.company.project.impl.MyWebDriverLookup}</li>
 *          </ul>
 *     </li>
 *     <li>Step 3: Enable extensions auto-detection in {@code resources/junit-platform.properties}
 *          <pre>{@code junit.jupiter.extensions.autodetection.enabled=true}</pre>
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
public class WebDriverJUnit5 implements InvocationInterceptor {
    /**
     * Mark events before an invocation.
     */
    protected static final String BE = "BE";
    /**
     * Mark events inside an invocation.
     */
    protected static final String BO = "BO";
    /**
     * Mark events after an invocation.
     */
    protected static final String AF = "AF";
    private static final Logger log = LoggerFactory.getLogger(WebDriverJUnit5.class);
    /**
     * The context of a reflective invocation of an executable (method or constructor).
     */
    protected static ReflectiveInvocationContext<Method> invocationContext;
    /**
     * The current {@link WebDriver} from the current JUnit5 test.
     */
    protected WebDriver driver;

    /**
     * Default constructor.
     */
    public WebDriverJUnit5() { /**/ }

    //-------------------------------------------------------------------------------//

    /**
     * {@inheritDoc}
     */
    @Override
    public void interceptBeforeAllMethod(Invocation<Void> invocation,
                                         ReflectiveInvocationContext<Method> invocationContext,
                                         ExtensionContext extensionContext) throws Throwable {
        lookupDriver(invocationContext, BE);
        invocation.proceed();
        lookupDriver(invocationContext, AF);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void interceptBeforeEachMethod(Invocation<Void> invocation,
                                          ReflectiveInvocationContext<Method> invocationContext,
                                          ExtensionContext extensionContext) throws Throwable {
        lookupDriver(invocationContext, BE);
        invocation.proceed();
        lookupDriver(invocationContext, AF);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void interceptTestMethod(Invocation<Void> invocation,
                                    ReflectiveInvocationContext<Method> invocationContext,
                                    ExtensionContext extensionContext) throws Throwable {
        lookupDriver(invocationContext, BE);
        invocation.proceed();
        lookupDriver(invocationContext, AF);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T interceptTestFactoryMethod(Invocation<T> invocation,
                                            ReflectiveInvocationContext<Method> invocationContext,
                                            ExtensionContext extensionContext) throws Throwable {
        lookupDriver(invocationContext, BE);
        T result = invocation.proceed();
        lookupDriver(invocationContext, AF);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void interceptTestTemplateMethod(Invocation<Void> invocation,
                                            ReflectiveInvocationContext<Method> invocationContext,
                                            ExtensionContext extensionContext) throws Throwable {
        lookupDriver(invocationContext, BE);
        invocation.proceed();
        lookupDriver(invocationContext, AF);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void interceptAfterEachMethod(Invocation<Void> invocation,
                                         ReflectiveInvocationContext<Method> invocationContext,
                                         ExtensionContext extensionContext) throws Throwable {
        lookupDriver(invocationContext, BE);
        invocation.proceed();
        lookupDriver(invocationContext, AF);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void interceptAfterAllMethod(Invocation<Void> invocation,
                                        ReflectiveInvocationContext<Method> invocationContext,
                                        ExtensionContext extensionContext) throws Throwable {
        lookupDriver(invocationContext, BE);
        invocation.proceed();
        lookupDriver(invocationContext, AF);
    }

    //-------------------------------------------------------------------------------//

    /**
     * Lookup {@link WebDriver} from the current {@link ReflectiveInvocationContext}.
     *
     * @param context The context of a reflective invocation of an executable (method or constructor).
     * @param aspect  Mark events before (BE) and after (AF) an invocation. Besides that there is body (BO).
     */
    protected void lookupDriver(ReflectiveInvocationContext<Method> context, String aspect) {
        invocationContext = context;
        Optional<Object> optInstance = context.getTarget();
        Object instance;
        Class<?> clazz;

        if (optInstance.isPresent()) {
            instance = optInstance.get();
            clazz = instance.getClass();
        } else {
            instance = context.getTargetClass();
            clazz = context.getTargetClass();
        }

        Field[] fields = FieldUtils.getAllFields(clazz);
        for (Field field : fields) {
            field.setAccessible(true);
            Object value;

            try {
                value = field.get(instance);
            } catch (IllegalAccessException e) {
                String fieldName = field.getName();
                String clazzName = clazz.getName();
                String msg = String.format("Read field %s in class %s", fieldName, clazzName);
                log.error(msg);
                throw new RuntimeError(msg, e);
            }

            if (value instanceof WebDriver) {
                driver = (WebDriver) value;
                break;
            }
        }

        Method method = Commons.readField(context, "method");
        String annotation = getSignatureAnnotation(method).getSimpleName();
        log.debug("{} @{} {} -> {}", aspect, annotation, method, driver);
    }

    /**
     * Get signature annotation of the current method is invoking.
     *
     * @param method The current method is invoking.
     * @return The annotation of the current method.
     */
    protected Class<?> getSignatureAnnotation(Method method) {
        Class<?>[] signatures = new Class[]{
                BeforeAll.class, BeforeEach.class,
                Test.class, ParameterizedTest.class, TestFactory.class, RepeatedTest.class, TestTemplate.class,
                AfterEach.class, AfterAll.class
        };
        Annotation[] declarations = method.getDeclaredAnnotations();

        for (Class<?> signature : signatures) {
            for (Annotation declaration : declarations) {
                if (signature.getName().equals(declaration.annotationType().getName())) {
                    return signature;
                }
            }
        }

        String msg = String.format("Get signature annotation at %s", method);
        log.error(msg);
        throw new RuntimeError(msg);
    }

    //-------------------------------------------------------------------------------//

    /*
     * Lifecycle Callbacks: https://www.baeldung.com/junit-5-extensions
     * 01. BeforeAllCallback.beforeAll
     * 02. InvocationInterceptor.interceptBeforeAllMethod
     * 03. @BeforeAll
     *
     * 04. InvocationInterceptor.interceptTestClassConstructor
     *
     * 05. BeforeEachCallback.beforeEach
     * 06. InvocationInterceptor.interceptBeforeEachMethod
     * 07. @BeforeEach
     *
     * 08. BeforeTestExecutionCallback.beforeTestExecution
     * 09. InvocationInterceptor.interceptTestMethod
     * 10. @Test
     * 11. AfterTestExecutionCallback.afterTestExecution
     *
     * 12. AfterEachCallback.afterEach
     * 13. InvocationInterceptor.interceptAfterEachMethod
     * 14. @BeforeEach
     *
     * 15. @AfterAll
     * 16. InvocationInterceptor.interceptAfterAllMethod
     * 17. AfterAllCallback.afterAll
     * */
}
