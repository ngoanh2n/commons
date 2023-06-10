package com.github.ngoanh2n;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.internal.BaseTestMethod;
import org.testng.internal.ConstructorOrMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Lookup {@link WebDriver} from the current TestNG test.
 *
 * @author ngoanh2n
 */
public class WebDriverTestNG implements IInvokedMethodListener {
    private static final Logger log = LoggerFactory.getLogger(WebDriverTestNG.class);
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
    /**
     * The test result of the current test.
     */
    protected static ITestResult iTestResult;
    /**
     * The current {@link WebDriver} from the current TestNG test.
     */
    protected WebDriver driver;

    /**
     * Default constructor.
     */
    public WebDriverTestNG() { /* No implementation necessary */ }

    //-------------------------------------------------------------------------------//

    /**
     * {@inheritDoc}
     */
    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
        lookupDriver(testResult, BE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
        lookupDriver(testResult, AF);
    }

    //-------------------------------------------------------------------------------//

    /**
     * Lookup {@link WebDriver} from the current {@link ITestResult}.
     *
     * @param testResult The test result of the current test.
     * @param aspect     Mark events before (BE) and after (AF) an invocation. Besides that there is body (BO).
     */
    protected void lookupDriver(ITestResult testResult, String aspect) {
        iTestResult = testResult;
        Object instance = testResult.getInstance();
        Class<?> clazz = instance.getClass();
        Field[] fields = FieldUtils.getAllFields(clazz);

        for (Field field : fields) {
            Object value = Commons.readField(instance, field.getName());

            if (value instanceof WebDriver) {
                driver = (WebDriver) value;
                break;
            }
        }

        BaseTestMethod cm = Commons.readField(iTestResult, "m_method");
        ConstructorOrMethod com = Commons.readField(cm, "m_method");

        Method method = Commons.readField(com, "m_method");
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
                BeforeClass.class, BeforeMethod.class,
                Test.class,
                AfterClass.class, AfterMethod.class
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
     * https://www.javatpoint.com/testng-annotations
     * 01. ISuiteListener.onStart
     * 02. ITestListener.onStart
     *
     * 03. IClassListener.onBeforeClass
     *
     * 04. IInvokedMethodListener.beforeInvocation
     * 05. @BeforeClass
     * 06. IInvokedMethodListener.afterInvocation
     *
     * 07. IInvokedMethodListener.beforeInvocation
     * 08. @BeforeMethod
     * 09. IInvokedMethodListener.afterInvocation
     *
     * 10. ITestListener.onTestStart
     *
     * 11. IInvokedMethodListener.beforeInvocation
     * 12. @Test
     * 13. IInvokedMethodListener.afterInvocation
     *
     * 14. IInvokedMethodListener.beforeInvocation
     * 15. @AfterMethod
     * 16. IInvokedMethodListener.afterInvocation
     *
     * 17. IClassListener.onAfterClass
     *
     * 18. IInvokedMethodListener.beforeInvocation
     * 19. @AfterClass
     * 20. IInvokedMethodListener.afterInvocation
     *
     * 21. ITestListener.onFinish
     * 22. ISuiteListener.onFinish
     * */
}
