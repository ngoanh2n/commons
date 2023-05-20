package com.github.ngoanh2n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
public class CommonsTest {
    private static final String FIELD_NAME_1 = "field1";
    private static final String FIELD_NAME_2 = "field2";
    private static final String FIELD_NAME_3 = "field3";
    private static final String DEFAULT_VALUE = "default";
    private static final String ASSIGNED_VALUE = "assigned";

    @Test
    void writeReadField() {
        Class1 class1 = new Class1(DEFAULT_VALUE);

        Commons.writeField(class1, FIELD_NAME_1, ASSIGNED_VALUE);
        String value1 = Commons.readField(class1, FIELD_NAME_1);
        Assertions.assertEquals(ASSIGNED_VALUE, value1);

        Commons.writeField(class1, FIELD_NAME_2, ASSIGNED_VALUE);
        String value2 = Commons.readField(class1, FIELD_NAME_2);
        Assertions.assertEquals(ASSIGNED_VALUE, value2);

        Commons.writeField(class1, FIELD_NAME_3, ASSIGNED_VALUE);
        String value3 = Commons.readField(class1, FIELD_NAME_3);
        Assertions.assertEquals(ASSIGNED_VALUE, value3);
    }

    @Test
    void writeReadStaticField() {
        Commons.writeField(Class2.class, FIELD_NAME_1, ASSIGNED_VALUE);
        String value1 = Commons.readField(Class2.class, FIELD_NAME_1);
        Assertions.assertEquals(ASSIGNED_VALUE, value1);

        Commons.writeField(Class2.class, FIELD_NAME_3, ASSIGNED_VALUE);
        String value3 = Commons.readField(Class2.class, FIELD_NAME_3);
        Assertions.assertEquals(ASSIGNED_VALUE, value3);
    }

    static class ParentClass {
        private final static String field3 = DEFAULT_VALUE;
        private final String field1 = DEFAULT_VALUE;
    }

    static class Class1 extends ParentClass {
        private final static String field2 = DEFAULT_VALUE;
        private final String field1;

        public Class1(String value) {
            field1 = value;
        }
    }

    static class Class2 extends ParentClass {
        public static final String field1 = DEFAULT_VALUE;
    }
}
