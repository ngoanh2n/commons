package com.github.ngoanh2n;

import org.junit.jupiter.api.*;

/**
 * @author ngoanh2n
 */
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
public class EnabledIfPropertyTest {
    private static final String PROPERTY_NAME = "ngoanh2n";
    private static final String PROPERTY_VALUE = "EnabledIfPropertyTest";
    private static final Property<String> PROPERTY = Property.ofString(PROPERTY_NAME);

    @Nested
    @Order(1)
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    final class SetPropertyOnTestMethod {
        @BeforeAll
        void beforeAll() {
            Assertions.assertNull(System.getProperty(PROPERTY_NAME));
            Assertions.assertNull(PROPERTY.getValue());
            Assertions.assertNull(Property.ofString(PROPERTY_NAME).getValue());
        }

        @BeforeEach
        void beforeEach() {
            Assertions.assertEquals(PROPERTY_VALUE, System.getProperty(PROPERTY_NAME));
            Assertions.assertEquals(PROPERTY_VALUE, PROPERTY.getValue());
            Assertions.assertEquals(PROPERTY_VALUE, Property.ofString(PROPERTY_NAME).getValue());
        }

        @Test
        @SetProperty(name = PROPERTY_NAME, value = PROPERTY_VALUE)
        @EnabledIfProperty(name = PROPERTY_NAME, value = PROPERTY_VALUE)
        void test() {
            Assertions.assertEquals(PROPERTY_VALUE, System.getProperty(PROPERTY_NAME));
            Assertions.assertEquals(PROPERTY_VALUE, PROPERTY.getValue());
            Assertions.assertEquals(PROPERTY_VALUE, Property.ofString(PROPERTY_NAME).getValue());
        }

        @AfterEach
        void afterEach() {
            Assertions.assertEquals(PROPERTY_VALUE, System.getProperty(PROPERTY_NAME));
            Assertions.assertEquals(PROPERTY_VALUE, PROPERTY.getValue());
            Assertions.assertEquals(PROPERTY_VALUE, Property.ofString(PROPERTY_NAME).getValue());
        }

        @AfterAll
        void afterAll() {
            Assertions.assertNull(System.getProperty(PROPERTY_NAME));
            Assertions.assertNull(PROPERTY.getValue());
            Assertions.assertNull(Property.ofString(PROPERTY_NAME).getValue());
        }
    }

    @Nested
    @Order(2)
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @SetProperty(name = PROPERTY_NAME, value = PROPERTY_VALUE)
    final class SetPropertyOnTestClass {
        @BeforeAll
        void beforeAll() {
            Assertions.assertEquals(PROPERTY_VALUE, System.getProperty(PROPERTY_NAME));
            Assertions.assertEquals(PROPERTY_VALUE, PROPERTY.getValue());
            Assertions.assertEquals(PROPERTY_VALUE, Property.ofString(PROPERTY_NAME).getValue());
        }

        @BeforeEach
        void beforeEach() {
            Assertions.assertEquals(PROPERTY_VALUE, System.getProperty(PROPERTY_NAME));
            Assertions.assertEquals(PROPERTY_VALUE, PROPERTY.getValue());
            Assertions.assertEquals(PROPERTY_VALUE, Property.ofString(PROPERTY_NAME).getValue());
        }

        @Test
        @EnabledIfProperty(name = PROPERTY_NAME, value = PROPERTY_VALUE)
        void test() {
            Assertions.assertEquals(PROPERTY_VALUE, System.getProperty(PROPERTY_NAME));
            Assertions.assertEquals(PROPERTY_VALUE, PROPERTY.getValue());
            Assertions.assertEquals(PROPERTY_VALUE, Property.ofString(PROPERTY_NAME).getValue());
        }

        @AfterEach
        void afterEach() {
            Assertions.assertEquals(PROPERTY_VALUE, System.getProperty(PROPERTY_NAME));
            Assertions.assertEquals(PROPERTY_VALUE, PROPERTY.getValue());
            Assertions.assertEquals(PROPERTY_VALUE, Property.ofString(PROPERTY_NAME).getValue());
        }

        @AfterAll
        void afterAll() {
            Assertions.assertEquals(PROPERTY_VALUE, System.getProperty(PROPERTY_NAME));
            Assertions.assertEquals(PROPERTY_VALUE, PROPERTY.getValue());
            Assertions.assertEquals(PROPERTY_VALUE, Property.ofString(PROPERTY_NAME).getValue());
        }
    }
}
