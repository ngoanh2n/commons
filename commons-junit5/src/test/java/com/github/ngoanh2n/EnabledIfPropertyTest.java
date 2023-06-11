package com.github.ngoanh2n;

import org.junit.jupiter.api.*;

/**
 * @author ngoanh2n
 */
public class EnabledIfPropertyTest {
    private static final String NAME = "property";
    private static final String VALUE = "ngoanh2n";
    private static final Property<String> PROPERTY = Property.ofString(NAME);

    static final class EnabledIfPropertyBTest {
        @BeforeAll
        static void beforeAll() {
            Assertions.assertNull(System.getProperty(NAME));
            Assertions.assertNull(PROPERTY.getValue());
            Assertions.assertNull(Property.ofString(NAME).getValue());
        }

        @BeforeEach
        void beforeEach() {
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
            Assertions.assertEquals(VALUE, System.getProperty(NAME));
            Assertions.assertEquals(VALUE, PROPERTY.getValue());
            Assertions.assertEquals(VALUE, Property.ofString(NAME).getValue());
        }

        @AfterAll
        static void afterAll() {
            Assertions.assertNull(System.getProperty(NAME));
            Assertions.assertNull(PROPERTY.getValue());
            Assertions.assertNull(Property.ofString(NAME).getValue());
        }
    }

    @SetProperty(name = NAME, value = VALUE)
    static final class EnabledIfPropertyATest {
        @BeforeAll
        static void beforeAll() {
            Assertions.assertEquals(VALUE, System.getProperty(NAME));
            Assertions.assertEquals(VALUE, PROPERTY.getValue());
            Assertions.assertEquals(VALUE, Property.ofString(NAME).getValue());
        }

        @BeforeEach
        void beforeEach() {
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
            Assertions.assertEquals(VALUE, System.getProperty(NAME));
            Assertions.assertEquals(VALUE, PROPERTY.getValue());
            Assertions.assertEquals(VALUE, Property.ofString(NAME).getValue());
        }

        @AfterAll
        static void afterAll() {
            Assertions.assertEquals(VALUE, System.getProperty(NAME));
            Assertions.assertEquals(VALUE, PROPERTY.getValue());
            Assertions.assertEquals(VALUE, Property.ofString(NAME).getValue());
        }
    }
}
