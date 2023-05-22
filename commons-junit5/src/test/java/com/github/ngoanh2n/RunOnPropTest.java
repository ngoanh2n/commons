package com.github.ngoanh2n;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
public class RunOnPropTest {
    private static final String NAME = "prop";
    private static final String VALUE = "ngoanh2n";
    private static final Property<String> PROPERTY = Property.string(NAME);

    static final class RunOnPropertyBTest {
        private static final Logger logger = LoggerFactory.getLogger(RunOnPropertyBTest.class);

        @BeforeAll
        static void beforeAll() {
            logger.debug("" + System.getProperty(NAME));
            logger.debug("" + PROPERTY.getValue());
            logger.debug("" + Property.string(NAME).getValue());

            Assertions.assertNull(System.getProperty(NAME));
            Assertions.assertNull(PROPERTY.getValue());
            Assertions.assertNull(Property.string(NAME).getValue());
        }

        @AfterAll
        static void afterAll() {
            logger.debug("" + System.getProperty(NAME));
            logger.debug("" + PROPERTY.getValue());
            logger.debug("" + Property.string(NAME).getValue());

            Assertions.assertNull(System.getProperty(NAME));
            Assertions.assertNull(PROPERTY.getValue());
            Assertions.assertNull(Property.string(NAME).getValue());
        }

        @BeforeEach
        void beforeEach() {
            logger.debug(System.getProperty(NAME));
            logger.debug(PROPERTY.getValue());
            logger.debug(Property.string(NAME).getValue());

            Assertions.assertEquals(VALUE, System.getProperty(NAME));
            Assertions.assertEquals(VALUE, PROPERTY.getValue());
            Assertions.assertEquals(VALUE, Property.string(NAME).getValue());
        }

        @Test
        @SetProp(name = NAME, value = VALUE)
        @RunOnProp(name = NAME, value = VALUE)
        void test() {
            Assertions.assertEquals(VALUE, System.getProperty(NAME));
            Assertions.assertEquals(VALUE, PROPERTY.getValue());
            Assertions.assertEquals(VALUE, Property.string(NAME).getValue());
        }

        @AfterEach
        void afterEach() {
            logger.debug(System.getProperty(NAME));
            logger.debug(PROPERTY.getValue());
            logger.debug(Property.string(NAME).getValue());

            Assertions.assertEquals(VALUE, System.getProperty(NAME));
            Assertions.assertEquals(VALUE, PROPERTY.getValue());
            Assertions.assertEquals(VALUE, Property.string(NAME).getValue());
        }
    }

    @SetProp(name = NAME, value = VALUE)
    static final class RunOnPropertyATest {
        private static final Logger logger = LoggerFactory.getLogger(RunOnPropertyATest.class);

        @BeforeAll
        static void beforeAll() {
            logger.debug(System.getProperty(NAME));
            logger.debug(PROPERTY.getValue());
            logger.debug(Property.string(NAME).getValue());

            Assertions.assertEquals(VALUE, System.getProperty(NAME));
            Assertions.assertEquals(VALUE, PROPERTY.getValue());
            Assertions.assertEquals(VALUE, Property.string(NAME).getValue());
        }

        @AfterAll
        static void afterAll() {
            logger.debug(System.getProperty(NAME));
            logger.debug(PROPERTY.getValue());
            logger.debug(Property.string(NAME).getValue());

            Assertions.assertEquals(VALUE, System.getProperty(NAME));
            Assertions.assertEquals(VALUE, PROPERTY.getValue());
            Assertions.assertEquals(VALUE, Property.string(NAME).getValue());
        }

        @BeforeEach
        void beforeEach() {
            logger.debug(System.getProperty(NAME));
            logger.debug(PROPERTY.getValue());
            logger.debug(Property.string(NAME).getValue());

            Assertions.assertEquals(VALUE, System.getProperty(NAME));
            Assertions.assertEquals(VALUE, PROPERTY.getValue());
            Assertions.assertEquals(VALUE, Property.string(NAME).getValue());
        }

        @Test
        @RunOnProp(name = NAME, value = VALUE)
        void test() {
            Assertions.assertEquals(VALUE, System.getProperty(NAME));
            Assertions.assertEquals(VALUE, PROPERTY.getValue());
            Assertions.assertEquals(VALUE, Property.string(NAME).getValue());
        }

        @AfterEach
        void afterEach() {
            logger.debug(System.getProperty(NAME));
            logger.debug(PROPERTY.getValue());
            logger.debug(Property.string(NAME).getValue());

            Assertions.assertEquals(VALUE, System.getProperty(NAME));
            Assertions.assertEquals(VALUE, PROPERTY.getValue());
            Assertions.assertEquals(VALUE, Property.string(NAME).getValue());
        }
    }
}
