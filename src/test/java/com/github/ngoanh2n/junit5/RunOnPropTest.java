package com.github.ngoanh2n.junit5;

import com.github.ngoanh2n.Prop;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 * @version 1.0.0
 * @since 2021-01-16
 */
public class RunOnPropTest {
    private static final Prop<String> prop = Prop.string("ngoanh2n");
    private static final String expectedPropValue = "Ho Huu Ngoan";

    @SetProp(name = "ngoanh2n", value = "Ho Huu Ngoan")
    static final class RunOnPropATest {
        private static final Logger logger = LoggerFactory.getLogger(RunOnPropATest.class);

        @BeforeAll
        static void beforeAll() {
            logger.debug("@BeforeAll: " + prop.getValue());
            Assertions.assertEquals(expectedPropValue, prop.getValue());
        }

        @BeforeEach
        void beforeEach() {
            logger.debug("@BeforeEach: " + prop.getValue());
            Assertions.assertEquals(expectedPropValue, prop.getValue());
        }

        @Test
        @RunOnProp(name = "ngoanh2n", value = "Ho Huu Ngoan")
        void test() {
            Assertions.assertEquals(expectedPropValue, prop.getValue());
        }

        @AfterEach
        void afterEach() {
            logger.debug("@AfterEach: " + prop.getValue());
            Assertions.assertEquals(expectedPropValue, prop.getValue());
        }

        @AfterAll
        static void afterAll() {
            logger.debug("@AfterAll: " + prop.getValue());
            Assertions.assertEquals(expectedPropValue, prop.getValue());
        }
    }

    static final class RunOnPropBTest {
        private static final Logger logger = LoggerFactory.getLogger(RunOnPropBTest.class);

        @BeforeAll
        static void beforeAll() {
            logger.debug("@BeforeAll: " + prop.getValue());
            Assertions.assertNull(prop.getValue());
        }

        @BeforeEach
        void beforeEach() {
            logger.debug("@BeforeAll: " + prop.getValue());
            Assertions.assertEquals(expectedPropValue, prop.getValue());
        }

        @Test
        @SetProp(name = "ngoanh2n", value = "Ho Huu Ngoan")
        @RunOnProp(name = "ngoanh2n", value = "Ho Huu Ngoan")
        void test() {
            Assertions.assertEquals(expectedPropValue, prop.getValue());
        }

        @AfterEach
        void afterEach() {
            logger.debug("@AfterEach: " + prop.getValue());
            Assertions.assertEquals(expectedPropValue, prop.getValue());
        }

        @AfterAll
        static void afterAll() {
            logger.debug("@AfterAll: " + prop.getValue());
            Assertions.assertNull(prop.getValue());
        }
    }
}
