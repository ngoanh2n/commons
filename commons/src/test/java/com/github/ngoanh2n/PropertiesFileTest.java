package com.github.ngoanh2n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author ngoanh2n
 */
public class PropertiesFileTest {
    private static final String PROP_NAME1 = "PropFile.PropName1";
    private static final String PROP_NAME2 = "PropFile.PropName2";
    private static final String PROP_NAME3 = "PropFile.PropName3";
    private static final String PROP_NAME4 = "PropFile.PropName4";

    private static final String PROP_VALUE0 = "PropValue0";
    private static final String PROP_VALUE1 = "PropValue1";
    private static final String PROP_VALUE2 = "PropValue2";
    private static final String PROP_VALUE3 = "PropValue3";

    /*
     * JVM System Property: No
     * Properties file: No
     * Property<?> object: Yes
     * */
    @Test
    void test1() {
        PropertiesFile propertiesFile = new PropertiesFile("File1.properties");

        /*
         * Value in Properties file: NO-FILE
         * Value in Property<?> object: NULL
         * => Value: NULL
         * */
        Property<String> property1 = new Property<>(PROP_NAME1, String.class);
        Assertions.assertNull(propertiesFile.getProperty(property1));

        /*
         * Value in Properties file: NO-FILE
         * Value in Property<?> object: PropValue2
         * => Value: PropValue2
         * */
        Property<String> property2 = new Property<>(PROP_NAME2, String.class, PROP_VALUE2);
        Assertions.assertEquals(PROP_VALUE2, propertiesFile.getProperty(property2));
    }

    /*
     * JVM System Property: No
     * Properties file: Yes
     * Property<?> object: Yes
     * */
    @Test
    void test2() {
        PropertiesFile propertiesFile = new PropertiesFile("com/github/ngoanh2n/PropFileTest/File2.properties");

        /*
         * Value in JVM System Property: NULL
         * Value in Properties file: PropValue1
         * Value in Property<?> object: NULL
         * => Value: PropValue1
         * */
        Property<String> property1 = new Property<>(PROP_NAME1, String.class);
        Assertions.assertEquals(PROP_VALUE1, propertiesFile.getProperty(property1));

        /*
         * Value in JVM System Property: NULL
         * Value in Properties file: PropValue2
         * Value in Property<?> object: PropValue0
         * => Value: PropValue2
         * */
        Property<String> property2 = new Property<>(PROP_NAME2, String.class, PROP_VALUE0);
        Assertions.assertEquals(PROP_VALUE2, propertiesFile.getProperty(property2));

        /*
         * Value in JVM System Property: NULL
         * Value in Properties file: NULL
         * Value in Property<?> object: PropValue3
         * => Value: PropValue3
         * */
        Property<String> property3 = new Property<>(PROP_NAME3, String.class, PROP_VALUE3);
        Assertions.assertEquals(PROP_VALUE3, propertiesFile.getProperty(property3));

        /*
         * Value in JVM System Property: NULL
         * Value in Properties file: NULL
         * Value in Property<?> object: NULL
         * => Value: NULL
         * */
        Property<String> property4 = new Property<>(PROP_NAME4, String.class);
        Assertions.assertNull(propertiesFile.getProperty(property4));
    }

    /*
     * JVM System Property: Yes
     * Properties file: Yes
     * Property<?> object: Yes
     * */
    @Test
    void test3() {
        PropertiesFile propertiesFile = new PropertiesFile("com/github/ngoanh2n/PropFileTest/File3.properties");

        /*
         * Value in JVM System Property: PropValue0
         * Value in Properties file: NULL
         * Value in Property<?> object: NULL
         * => Value: PropValue0
         * */
        System.setProperty(PROP_NAME1, PROP_VALUE0);
        Property<String> property1 = new Property<>(PROP_NAME1, String.class);
        Assertions.assertEquals(PROP_VALUE0, propertiesFile.getProperty(property1));

        /*
         * Value in JVM System Property: PropValue0
         * Value in Properties file: PropValue2
         * Value in Property<?> object: NULL
         * => Value: PropValue0
         * */
        System.setProperty(PROP_NAME2, PROP_VALUE0);
        Property<String> property2 = new Property<>(PROP_NAME2, String.class, PROP_VALUE2);
        Assertions.assertEquals(PROP_VALUE0, propertiesFile.getProperty(property2));

        /*
         * Value in JVM System Property: PropValue0
         * Value in Properties file: PropValue3
         * Value in Property<?> object: PropValue3
         * => Value: PropValue0
         * */
        System.setProperty(PROP_NAME3, PROP_VALUE0);
        Property<String> property3 = new Property<>(PROP_NAME3, String.class, PROP_VALUE3);
        Assertions.assertEquals(PROP_VALUE0, propertiesFile.getProperty(property3));

        /*
         * Value in JVM System Property: ENUM0
         * Value in Properties file: ENUM1
         * Value in Property<?> object: ENUM2
         * => Value: ENUM0
         * */
        System.setProperty(PROP_NAME4, TestEnum.ENUM0.name());
        Property<TestEnum> property4 = new Property<>(PROP_NAME4, TestEnum.class, TestEnum.ENUM2);
        Assertions.assertEquals(TestEnum.ENUM0, propertiesFile.getProperty(property4));
    }

    enum TestEnum {
        ENUM0, ENUM1, ENUM2;
    }
}

