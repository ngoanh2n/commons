package com.github.ngoanh2n;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
public class EnabledIfPropertyTest {
    private static final String NAME = "prop";
    private static final String VALUE = "ngoanh2n";
    private static final Property<String> PROPERTY = Property.ofString(NAME);

    static final class EnabledIfPropertyBTest {
        private static final Logger logger = LoggerFactory.getLogger(EnabledIfPropertyBTest.class);

        @BeforeAll
        static void beforeAll() {
            logger.debug("" + System.getProperty(NAME));
            logger.debug("" + PROPERTY.getValue());
            logger.debug("" + Property.ofString(NAME).getValue());

            Assertions.assertNull(System.getProperty(NAME));
            Assertions.assertNull(PROPERTY.getValue());
            Assertions.assertNull(Property.ofString(NAME).getValue());
        }

        @AfterAll
        static void afterAll() {
            logger.debug("" + System.getProperty(NAME));
            logger.debug("" + PROPERTY.getValue());
            logger.debug("" + Property.ofString(NAME).getValue());

            Assertions.assertNull(System.getProperty(NAME));
            Assertions.assertNull(PROPERTY.getValue());
            Assertions.assertNull(Property.ofString(NAME).getValue());
        }

        @BeforeEach
        void beforeEach() {
            logger.debug(System.getProperty(NAME));
            logger.debug(PROPERTY.getValue());
            logger.debug(Property.ofString(NAME).getValue());

            Assertions.assertEquals(VALUE, System.getProperty(NAME));
            Assertions.assertEquals(VALUE, PROPERTY.getValue());
            Assertions.assertEquals(VALUE, Property.ofString(NAME).getValue());
        }

        @Test
        @SetProperty(name = NAME, value = VALUE)
        @EnabledIfProperty(name = NAME, value = VALUE)
        void test() {
            Assertions.assertEquals(VALUE, System.getProperty(NAME));
            Assertions.assertEquals(VALUE, PROPERTY.getValue());
            Assertions.assertEquals(VALUE, Property.ofString(NAME).getValue());
        }

        @AfterEach
        void afterEach() {
            logger.debug(System.getProperty(NAME));
            logger.debug(PROPERTY.getValue());
            logger.debug(Property.ofString(NAME).getValue());

            Assertions.assertEquals(VALUE, System.getProperty(NAME));
            Assertions.assertEquals(VALUE, PROPERTY.getValue());
            Assertions.assertEquals(VALUE, Property.ofString(NAME).getValue());
        }
    }

    @SetProperty(name = NAME, value = VALUE)
    static final class EnabledIfPropertyATest {
        private static final Logger logger = LoggerFactory.getLogger(EnabledIfPropertyATest.class);

        @BeforeAll
        static void beforeAll() {
            logger.debug(System.getProperty(NAME));
            logger.debug(PROPERTY.getValue());
            logger.debug(Property.ofString(NAME).getValue());

            Assertions.assertEquals(VALUE, System.getProperty(NAME));
            Assertions.assertEquals(VALUE, PROPERTY.getValue());
            Assertions.assertEquals(VALUE, Property.ofString(NAME).getValue());
        }

        @AfterAll
        static void afterAll() {
            logger.debug(System.getProperty(NAME));
            logger.debug(PROPERTY.getValue());
            logger.debug(Property.ofString(NAME).getValue());

            Assertions.assertEquals(VALUE, System.getProperty(NAME));
            Assertions.assertEquals(VALUE, PROPERTY.getValue());
            Assertions.assertEquals(VALUE, Property.ofString(NAME).getValue());
        }

        @BeforeEach
        void beforeEach() {
            logger.debug(System.getProperty(NAME));
            logger.debug(PROPERTY.getValue());
            logger.debug(Property.ofString(NAME).getValue());

            Assertions.assertEquals(VALUE, System.getProperty(NAME));
            Assertions.assertEquals(VALUE, PROPERTY.getValue());
            Assertions.assertEquals(VALUE, Property.ofString(NAME).getValue());
        }

        @Test
        @EnabledIfProperty(name = NAME, value = VALUE)
        void test() {
            Assertions.assertEquals(VALUE, System.getProperty(NAME));
            Assertions.assertEquals(VALUE, PROPERTY.getValue());
            Assertions.assertEquals(VALUE, Property.ofString(NAME).getValue());
        }

        @AfterEach
        void afterEach() {
            logger.debug(System.getProperty(NAME));
            logger.debug(PROPERTY.getValue());
            logger.debug(Property.ofString(NAME).getValue());

            Assertions.assertEquals(VALUE, System.getProperty(NAME));
            Assertions.assertEquals(VALUE, PROPERTY.getValue());
            Assertions.assertEquals(VALUE, Property.ofString(NAME).getValue());
        }
    }
}
