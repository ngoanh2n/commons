package com.github.ngoanh2n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
public class PropFileTest {
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
     * Prop<?> object: Yes
     * */
    @Test
    void test1() {
        PropFile propFile = new PropFile("File1.properties");

        /*
         * Value in Properties file: NO-FILE
         * Value in Prop<?> object: NULL
         * => Value: NULL
         * */
        Prop<String> prop1 = new Prop<>(PROP_NAME1, String.class);
        Assertions.assertNull(propFile.getPropValue(prop1));

        /*
         * Value in Properties file: NO-FILE
         * Value in Prop<?> object: PropValue2
         * => Value: PropValue2
         * */
        Prop<String> prop2 = new Prop<>(PROP_NAME2, String.class, PROP_VALUE2);
        Assertions.assertEquals(PROP_VALUE2, propFile.getPropValue(prop2));
    }

    /*
     * JVM System Property: No
     * Properties file: Yes
     * Prop<?> object: Yes
     * */
    @Test
    void test2() {
        PropFile propFile = new PropFile("com/github/ngoanh2n/PropFileTest/File2.properties");

        /*
         * Value in JVM System Property: NULL
         * Value in Properties file: PropValue1
         * Value in Prop<?> object: NULL
         * => Value: PropValue1
         * */
        Prop<String> prop1 = new Prop<>(PROP_NAME1, String.class);
        Assertions.assertEquals(PROP_VALUE1, propFile.getPropValue(prop1));

        /*
         * Value in JVM System Property: NULL
         * Value in Properties file: PropValue2
         * Value in Prop<?> object: PropValue0
         * => Value: PropValue2
         * */
        Prop<String> prop2 = new Prop<>(PROP_NAME2, String.class, PROP_VALUE0);
        Assertions.assertEquals(PROP_VALUE2, propFile.getPropValue(prop2));

        /*
         * Value in JVM System Property: NULL
         * Value in Properties file: NULL
         * Value in Prop<?> object: PropValue3
         * => Value: PropValue3
         * */
        Prop<String> prop3 = new Prop<>(PROP_NAME3, String.class, PROP_VALUE3);
        Assertions.assertEquals(PROP_VALUE3, propFile.getPropValue(prop3));

        /*
         * Value in JVM System Property: NULL
         * Value in Properties file: NULL
         * Value in Prop<?> object: NULL
         * => Value: NULL
         * */
        Prop<String> prop4 = new Prop<>(PROP_NAME4, String.class);
        Assertions.assertNull(propFile.getPropValue(prop4));
    }

    /*
     * JVM System Property: Yes
     * Properties file: Yes
     * Prop<?> object: Yes
     * */
    @Test
    void test3() {
        PropFile propFile = new PropFile("com/github/ngoanh2n/PropFileTest/File3.properties");

        /*
         * Value in JVM System Property: PropValue0
         * Value in Properties file: NULL
         * Value in Prop<?> object: NULL
         * => Value: PropValue0
         * */
        System.setProperty(PROP_NAME1, PROP_VALUE0);
        Prop<String> prop1 = new Prop<>(PROP_NAME1, String.class);
        Assertions.assertEquals(PROP_VALUE0, propFile.getPropValue(prop1));

        /*
         * Value in JVM System Property: PropValue0
         * Value in Properties file: PropValue2
         * Value in Prop<?> object: NULL
         * => Value: PropValue0
         * */
        System.setProperty(PROP_NAME2, PROP_VALUE0);
        Prop<String> prop2 = new Prop<>(PROP_NAME2, String.class, PROP_VALUE2);
        Assertions.assertEquals(PROP_VALUE0, propFile.getPropValue(prop2));

        /*
         * Value in JVM System Property: PropValue0
         * Value in Properties file: PropValue3
         * Value in Prop<?> object: PropValue3
         * => Value: PropValue0
         * */
        System.setProperty(PROP_NAME3, PROP_VALUE0);
        Prop<String> prop3 = new Prop<>(PROP_NAME3, String.class, PROP_VALUE3);
        Assertions.assertEquals(PROP_VALUE0, propFile.getPropValue(prop3));

        /*
         * Value in JVM System Property: ENUM0
         * Value in Properties file: ENUM1
         * Value in Prop<?> object: ENUM2
         * => Value: ENUM0
         * */
        System.setProperty(PROP_NAME4, TestEnum.ENUM0.name());
        Prop<TestEnum> prop4 = new Prop<>(PROP_NAME4, TestEnum.class, TestEnum.ENUM2);
        Assertions.assertEquals(TestEnum.ENUM0, propFile.getPropValue(prop4));
    }

    enum TestEnum {
        ENUM0, ENUM1, ENUM2;
    }
}

