package com.github.ngoanh2n;

import org.junit.jupiter.api.*;

/**
 * System property (A), Properties file (B), Default value (C)
 *
 * @author ngoanh2n
 */
public class PropertiesFileTest {
    private static final String PROP_NAME1 = "PropertiesFile.PropertyName1";
    private static final String PROP_NAME2 = "PropertiesFile.PropertyName2";
    private static final String PROP_NAME3 = "PropertiesFile.PropertyName3";
    private static final String PROP_NAME4 = "PropertiesFile.PropertyName4";

    private static final String PROP_VALUE1 = "PropertyValue1";
    private static final String PROP_VALUE2 = "PropertyValue2";
    private static final String PROP_VALUE3 = "PropertyValue3";
    private static final String PROP_VALUE4 = "PropertyValue4";

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class WithoutFile {
        PropertiesFile propertiesFile = new PropertiesFile("com/github/ngoanh2n/PropFileTest/File1.properties");

        @Test
        @DisplayName("{A: NO, B: NO, C: NO}")
        void test1() {
            Property<String> property1 = new Property<>(PROP_NAME1, String.class);
            Assertions.assertNull(propertiesFile.getProperty(property1));
        }

        @Test
        @DisplayName("{A: NO, B: NO, C: YES}")
        void test2() {
            Property<String> property2 = new Property<>(PROP_NAME2, String.class, PROP_VALUE2);
            Assertions.assertEquals(PROP_VALUE2, propertiesFile.getProperty(property2));
        }

        @Test
        @DisplayName("{A: YES, B: NO, C: NO}")
        void test3() {
            System.setProperty(PROP_NAME3, PROP_VALUE3);
            Property<String> property3 = new Property<>(PROP_NAME3, String.class);
            Assertions.assertEquals(PROP_VALUE3, propertiesFile.getProperty(property3));
        }

        @Test
        @DisplayName("{A: YES, B: NO, C: YES}")
        void test4() {
            System.setProperty(PROP_NAME4, PROP_VALUE4);
            Property<String> property4 = new Property<>(PROP_NAME4, String.class, PROP_VALUE4);
            Assertions.assertEquals(PROP_VALUE4, propertiesFile.getProperty(property4));
        }

        @AfterAll
        void cleanup() {
            System.clearProperty(PROP_NAME1);
            System.clearProperty(PROP_NAME2);
            System.clearProperty(PROP_NAME3);
            System.clearProperty(PROP_NAME4);
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class WithFile {
        PropertiesFile propertiesFile = new PropertiesFile("com/github/ngoanh2n/PropFileTest/File2.properties");

        @Test
        @DisplayName("{A: NO, B: YES, C: NO}")
        void test1() {
            Property<String> property1 = new Property<>(PROP_NAME1, String.class);
            Assertions.assertEquals(PROP_VALUE1, propertiesFile.getProperty(property1));
        }

        @Test
        @DisplayName("{A: NO, B: YES, C: YES}")
        void test2() {
            Property<String> property2 = new Property<>(PROP_NAME2, String.class, PROP_VALUE2);
            Assertions.assertEquals(PROP_VALUE2, propertiesFile.getProperty(property2));
        }

        @Test
        @DisplayName("{A: YES, B: YES, C: NO}")
        void test3() {
            System.setProperty(PROP_NAME3, PROP_VALUE3);
            Property<String> property3 = new Property<>(PROP_NAME3, String.class);
            Assertions.assertEquals(PROP_VALUE3, propertiesFile.getProperty(property3));
        }

        @Test
        @DisplayName("{A: YES, B: YES, C: YES}")
        void test4() {
            System.setProperty(PROP_NAME4, PROP_VALUE4);
            Property<String> property4 = new Property<>(PROP_NAME4, String.class, PROP_VALUE4);
            Assertions.assertEquals(PROP_VALUE4, propertiesFile.getProperty(property4));
        }

        @AfterAll
        void cleanup() {
            System.clearProperty(PROP_NAME1);
            System.clearProperty(PROP_NAME2);
            System.clearProperty(PROP_NAME3);
            System.clearProperty(PROP_NAME4);
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class WithFileHasEnum {
        PropertiesFile propertiesFile = new PropertiesFile("com/github/ngoanh2n/PropFileTest/File3.properties");

        @Test
        @DisplayName("{A: NO, B: YES, C: NO}")
        void test1() {
            System.setProperty(PROP_NAME1, TestEnum.ENUM2.name());
            Property<TestEnum> property1 = new Property<>(PROP_NAME1, TestEnum.class, TestEnum.ENUM1);
            Assertions.assertEquals(TestEnum.ENUM2, propertiesFile.getProperty(property1));
        }

        @Test
        @DisplayName("{A: NO, B: YES, C: YES}")
        void test2() {
            Property<TestEnum> property2 = new Property<>(PROP_NAME2, TestEnum.class, TestEnum.ENUM1);
            Assertions.assertEquals(TestEnum.ENUM2, propertiesFile.getProperty(property2));
        }

        @AfterAll
        void cleanup() {
            System.clearProperty(PROP_NAME1);
            System.clearProperty(PROP_NAME2);
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class WithFileReAssignValue {
        PropertiesFile propertiesFile = new PropertiesFile("com/github/ngoanh2n/PropFileTest/File4.properties");

        @Test
        @DisplayName("{A: NO, B: YES, C: YES}")
        void test2() {
            Property<String> property2 = new Property<>(PROP_NAME2, String.class, PROP_VALUE2);
            property2.setValue(null);
            Assertions.assertNull(propertiesFile.getProperty(property2));

            property2 = new Property<>(PROP_NAME2, String.class, PROP_VALUE2);
            property2.setValue(PROP_VALUE1);
            Assertions.assertEquals(PROP_VALUE1, propertiesFile.getProperty(property2));
        }

        @Test
        @DisplayName("{A: YES, B: YES, C: YES}")
        void test4() {
            System.setProperty(PROP_NAME4, PROP_VALUE4);
            Property<String> property4 = new Property<>(PROP_NAME1, String.class, PROP_VALUE1);
            property4.setValue(null);
            Assertions.assertNull(propertiesFile.getProperty(property4));

            property4 = new Property<>(PROP_NAME1, String.class, PROP_VALUE1);
            property4.setValue(PROP_VALUE3);
            Assertions.assertEquals(PROP_VALUE3, propertiesFile.getProperty(property4));
        }

        @AfterAll
        void cleanup() {
            System.clearProperty(PROP_NAME2);
            System.clearProperty(PROP_NAME4);
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class WithFileToSystem {
        PropertiesFile propertiesFile = new PropertiesFile("com/github/ngoanh2n/PropFileTest/File2.properties", true);

        @Test
        @DisplayName("To System")
        void test() {
            Assertions.assertEquals(4, propertiesFile.getProperties().size());
            Assertions.assertEquals(PROP_VALUE4, System.getProperty(PROP_NAME4));
        }

        @AfterAll
        void cleanup() {
            System.clearProperty(PROP_NAME4);
        }
    }

    enum TestEnum {
        ENUM1, ENUM2;
    }
}

