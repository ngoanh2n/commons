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
    private static final String NAME = "prop";
    private static final String VALUE = "ngoanh2n";
    private static final Prop<String> prop = Prop.string(NAME);

    static final class RunOnPropBTest {
        private static final Logger logger = LoggerFactory.getLogger(RunOnPropBTest.class);

        @BeforeAll
        static void beforeAll() {
            logger.debug("");
            logger.debug("@BeforeAll: " + System.getProperty(NAME));
            logger.debug("@BeforeAll: " + prop.getValue());
            logger.debug("@BeforeAll: " + Prop.string(NAME).getValue());

            Assertions.assertNull(System.getProperty(NAME));
            Assertions.assertNull(prop.getValue());
            Assertions.assertNull(Prop.string(NAME).getValue());
        }

        @BeforeEach
        void beforeEach() {
            logger.debug("");
            logger.debug("@BeforeEach: " + System.getProperty(NAME));
            logger.debug("@BeforeEach: " + prop.getValue());
            logger.debug("@BeforeEach: " + Prop.string(NAME).getValue());

            Assertions.assertEquals(VALUE, System.getProperty(NAME));
            Assertions.assertEquals(VALUE, prop.getValue());
            Assertions.assertEquals(VALUE, Prop.string(NAME).getValue());
        }

        @Test
        @SetProp(name = NAME, value = VALUE)
        @RunOnProp(name = NAME, value = VALUE)
        void test() {
            Assertions.assertEquals(VALUE, System.getProperty(NAME));
            Assertions.assertEquals(VALUE, prop.getValue());
            Assertions.assertEquals(VALUE, Prop.string(NAME).getValue());
        }

        @AfterEach
        void afterEach() {
            logger.debug("");
            logger.debug("@AfterEach: " + System.getProperty(NAME));
            logger.debug("@AfterEach: " + prop.getValue());
            logger.debug("@AfterEach: " + Prop.string(NAME).getValue());

            Assertions.assertEquals(VALUE, System.getProperty(NAME));
            Assertions.assertEquals(VALUE, prop.getValue());
            Assertions.assertEquals(VALUE, Prop.string(NAME).getValue());
        }

        @AfterAll
        static void afterAll() {
            logger.debug("");
            logger.debug("@AfterAll: " + System.getProperty(NAME));
            logger.debug("@AfterAll: " + prop.getValue());
            logger.debug("@AfterAll: " + Prop.string(NAME).getValue());

            Assertions.assertNull(System.getProperty(NAME));
            Assertions.assertNull(prop.getValue());
            Assertions.assertNull(Prop.string(NAME).getValue());
        }
    }

    @SetProp(name = NAME, value = VALUE)
    static final class RunOnPropATest {
        private static final Logger logger = LoggerFactory.getLogger(RunOnPropATest.class);

        @BeforeAll
        static void beforeAll() {
            logger.debug("");
            logger.debug("@BeforeAll: " + System.getProperty(NAME));
            logger.debug("@BeforeAll: " + prop.getValue());
            logger.debug("@BeforeAll: " + Prop.string(NAME).getValue());

            Assertions.assertEquals(VALUE, System.getProperty(NAME));
            Assertions.assertEquals(VALUE, prop.getValue());
            Assertions.assertEquals(VALUE, Prop.string(NAME).getValue());
        }

        @BeforeEach
        void beforeEach() {
            logger.debug("");
            logger.debug("@BeforeEach: " + System.getProperty(NAME));
            logger.debug("@BeforeEach: " + prop.getValue());
            logger.debug("@BeforeEach: " + Prop.string(NAME).getValue());

            Assertions.assertEquals(VALUE, System.getProperty(NAME));
            Assertions.assertEquals(VALUE, prop.getValue());
            Assertions.assertEquals(VALUE, Prop.string(NAME).getValue());
        }

        @Test
        @RunOnProp(name = NAME, value = VALUE)
        void test() {
            logger.debug("");
            Assertions.assertEquals(VALUE, System.getProperty(NAME));
            Assertions.assertEquals(VALUE, prop.getValue());
            Assertions.assertEquals(VALUE, Prop.string(NAME).getValue());
        }

        @AfterEach
        void afterEach() {
            logger.debug("");
            logger.debug("@AfterEach: " + System.getProperty(NAME));
            logger.debug("@AfterEach: " + prop.getValue());
            logger.debug("@AfterEach: " + Prop.string(NAME).getValue());

            Assertions.assertEquals(VALUE, System.getProperty(NAME));
            Assertions.assertEquals(VALUE, prop.getValue());
            Assertions.assertEquals(VALUE, Prop.string(NAME).getValue());
        }

        @AfterAll
        static void afterAll() {
            logger.debug("");
            logger.debug("@AfterAll: " + System.getProperty(NAME));
            logger.debug("@AfterAll: " + prop.getValue());
            logger.debug("@AfterAll: " + Prop.string(NAME).getValue());

            Assertions.assertEquals(VALUE, System.getProperty(NAME));
            Assertions.assertEquals(VALUE, prop.getValue());
            Assertions.assertEquals(VALUE, Prop.string(NAME).getValue());
        }
    }
}
