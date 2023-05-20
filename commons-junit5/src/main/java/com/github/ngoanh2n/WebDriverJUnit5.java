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
 * Lookup the {@link WebDriver} from current JUnit5 tests.
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
public class WebDriverJUnit5 implements InvocationInterceptor {
    protected static final Logger log = LoggerFactory.getLogger(WebDriverJUnit5.class);
    protected static final String BE = "BE";
    protected static final String BO = "BO";
    protected static final String AF = "AF";
    protected static ReflectiveInvocationContext<Method> invocationContext;
    protected WebDriver driver;

    /**
     * Default constructor.
     */
    public WebDriverJUnit5() { /* No implementation necessary */ }

    //-------------------------------------------------------------------------------//

    protected static Class<?> getSignatureAnnotation(Method method) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void interceptBeforeAllMethod(Invocation<Void> invocation,
                                         ReflectiveInvocationContext<Method> invocationContext,
                                         ExtensionContext extensionContext) throws Throwable {
        getDriver(invocationContext, BE);
        invocation.proceed();
        getDriver(invocationContext, AF);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void interceptBeforeEachMethod(Invocation<Void> invocation,
                                          ReflectiveInvocationContext<Method> invocationContext,
                                          ExtensionContext extensionContext) throws Throwable {
        getDriver(invocationContext, BE);
        invocation.proceed();
        getDriver(invocationContext, AF);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void interceptTestMethod(Invocation<Void> invocation,
                                    ReflectiveInvocationContext<Method> invocationContext,
                                    ExtensionContext extensionContext) throws Throwable {
        getDriver(invocationContext, BE);
        invocation.proceed();
        getDriver(invocationContext, AF);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T interceptTestFactoryMethod(Invocation<T> invocation,
                                            ReflectiveInvocationContext<Method> invocationContext,
                                            ExtensionContext extensionContext) throws Throwable {
        getDriver(invocationContext, BE);
        T result = invocation.proceed();
        getDriver(invocationContext, AF);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void interceptTestTemplateMethod(Invocation<Void> invocation,
                                            ReflectiveInvocationContext<Method> invocationContext,
                                            ExtensionContext extensionContext) throws Throwable {
        getDriver(invocationContext, BE);
        invocation.proceed();
        getDriver(invocationContext, AF);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void interceptAfterEachMethod(Invocation<Void> invocation,
                                         ReflectiveInvocationContext<Method> invocationContext,
                                         ExtensionContext extensionContext) throws Throwable {
        getDriver(invocationContext, BE);
        invocation.proceed();
        getDriver(invocationContext, AF);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void interceptAfterAllMethod(Invocation<Void> invocation,
                                        ReflectiveInvocationContext<Method> invocationContext,
                                        ExtensionContext extensionContext) throws Throwable {
        getDriver(invocationContext, BE);
        invocation.proceed();
        getDriver(invocationContext, AF);
    }

    //-------------------------------------------------------------------------------//

    protected void getDriver(ReflectiveInvocationContext<Method> context, String aspect) {
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
